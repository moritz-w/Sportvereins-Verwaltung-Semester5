package at.fhv.sportsclub.controller.impl;

import at.fhv.sportsclub.controller.common.CommonController;
import at.fhv.sportsclub.controller.common.RequiredPrivileges;
import at.fhv.sportsclub.controller.interfaces.ITournamentController;
import at.fhv.sportsclub.entity.tournament.EncounterEntity;
import at.fhv.sportsclub.entity.tournament.ParticipantEntity;
import at.fhv.sportsclub.entity.tournament.TournamentEntity;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ModificationType;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.security.AccessLevel;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.team.TeamDTO;
import at.fhv.sportsclub.model.tournament.EncounterDTO;
import at.fhv.sportsclub.model.tournament.ParticipantDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;
import at.fhv.sportsclub.repository.tournament.TournamentRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import at.fhv.sportsclub.services.MessageGeneratorService;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope("prototype")
public class TournamentController extends CommonController<TournamentDTO, TournamentEntity, TournamentRepository> implements ITournamentController {

    private final TournamentRepository tournamentRepository;
    private final DepartmentController departmentController;
    private final TeamController teamController;
    private final MessageController messageController;

    private SessionDTO session;

    @Autowired
    public TournamentController(
            TournamentRepository repository,
            DepartmentController departmentController,
            TeamController teamController,
            MessageController messageController
    ) {
        super(repository, TournamentDTO.class, TournamentEntity.class);
        this.tournamentRepository = repository;
        this.departmentController = departmentController;
        this.teamController = teamController;
        this.messageController = messageController;
    }
    // region RMI wrapper methods
    @Override
    @RequiredPrivileges(category = "Tournament", accessLevel = {AccessLevel.READ})
    public ListWrapper<TournamentDTO> getAllEntries(SessionDTO session) throws RemoteException {
        return new ListWrapper<>(new ArrayList<>(this.getAll()), null);
    }

    @Override
    @RequiredPrivileges(category = "Tournament", accessLevel = {AccessLevel.READ})
    public TournamentDTO getEntryDetails(SessionDTO session, String id) throws RemoteException {
        return this.getDetails(id, true);
    }

    @Override
    @RequiredPrivileges(category = "Tournament", accessLevel = {AccessLevel.READ})
    public TournamentDTO getById(SessionDTO session, String id) throws RemoteException{
        return this.getDetails(id, false);
    }

    /**
     * Returns a list of tournaments, where a team with the given personId is set as a trainer, takes part
     * part in the tournament.
     * @param session
     * @param personId
     * @return
     */
    @Override
    @RequiredPrivileges(category = "Tournament", accessLevel = {AccessLevel.SPECIAL})
    public ListWrapper<TournamentDTO> getTournamentByTrainerId(SessionDTO session, String personId){
        ListWrapper<TeamDTO> teamsByTrainer = teamController.getTeamsByTrainerId(session, personId);
        if (teamsByTrainer.getResponse() != null){
            return new ListWrapper<>(null, teamsByTrainer.getResponse());
        }
        HashSet<ObjectId> teamIds = new HashSet<>();
        for (TeamDTO teamDTO : teamsByTrainer.getContents()) {
            teamIds.add(new ObjectId(teamDTO.getId()));
        }
        List<TournamentEntity> tournamentsByTeamId = tournamentRepository.getTournamentByTeamId(new ArrayList<>(teamIds));
        if (tournamentsByTeamId.isEmpty()) {
            return new ListWrapper<>(null, createErrorMessage("No tournaments found for the given teams"));
        }
        for (TournamentEntity tournamentEntity : tournamentsByTeamId) {
            List<ParticipantEntity> validParticipants = new ArrayList<>();
            for (ParticipantEntity participantEntity : tournamentEntity.getTeams()) {
                if (teamIds.contains(participantEntity.getTeam())){
                    validParticipants.add(participantEntity);
                }
            }
            if (!validParticipants.isEmpty()){
                tournamentEntity.setTeams(validParticipants);
            }
        }
        List<TournamentDTO> tournamentDTOList = mapAnyCollection(
                tournamentsByTeamId, TournamentDTO.class, "TournamentDTOMappingFull"
        );
        return new ListWrapper<>(new ArrayList<>(tournamentDTOList), null);
    }

    @Override
    @RequiredPrivileges(category = "Tournament", accessLevel = {AccessLevel.WRITE})
    public TournamentDTO saveOrUpdateEntry(SessionDTO session, TournamentDTO tournament){
        this.session = session;
        TournamentDTO emptyResult = new TournamentDTO();
        ResponseMessageDTO responseMessageDTO = this.validateDto(tournament);
        if (!responseMessageDTO.isValidated()) {
            emptyResult.setResponse(responseMessageDTO);
            return emptyResult;
        }
        // if properties in the tournament where modified like the league or tournament name (excludes arrays)
        if(tournament.getModificationType() == ModificationType.MODIFIED){
            denormalizeTournament(tournament);
        } else if (tournament.getModificationType() == ModificationType.REMOVED){
            //
        }
        // if the tournament is not yet existing, it is simply saved to the database
        if(tournament.getId() == null || tournament.getId().isEmpty()){

            for (ParticipantDTO participant : tournament.getTeams()) {
                denormalizeParticipant(participant);
                this.informCoaches(participant, tournament);
            }

            tournament.setEncounters(new ArrayList<>());
            ResponseMessageDTO saveResponse = this.saveOrUpdate(tournament, null, entity -> {
                for (ParticipantEntity participantEntity : entity.getTeams()) {
                    participantEntity.setId(new ObjectId().toHexString());
                }
            });
            if (saveResponse.isSuccess()) {
                return this.getDetails(saveResponse.getContextId(), true);
            } else {
                return rejectRequest(saveResponse.getInfoMessage());
            }
        }

        // save or update participating teams by pushing modified data to the document array over the corresponding repository methods
        if(tournament.getTeams() != null && !tournament.getTeams().isEmpty()){
            List<ParticipantDTO> updateCandidates = tournament.getTeams().stream()
                    .filter(participantDTO -> participantDTO.getModificationType() == ModificationType.MODIFIED)
                    .collect(Collectors.toList());
            List<ParticipantDTO> deleteCandidates = tournament.getTeams().stream()
                    .filter(participantDTO -> participantDTO.getModificationType() == ModificationType.REMOVED)
                    .collect(Collectors.toList());

            for (ParticipantDTO updateCandidate : updateCandidates) {
                denormalizeParticipant(updateCandidate);
                this.informCoaches(updateCandidate, tournament);
            }

            tournamentRepository.addTeamToTournament(
                    tournament.getId(), mapAnyCollection(updateCandidates, ParticipantEntity.class, "ParticipantMapping"));

            tournamentRepository.removeTeamFromTournament(
                    tournament.getId(), mapAnyCollection(deleteCandidates, ParticipantEntity.class, "ParticipantMapping")
            );
        }
        // save or update encounters
        if(tournament.getEncounters() != null && !tournament.getEncounters().isEmpty()){
            List<EncounterDTO> updateCandidates = tournament.getEncounters().stream()
                    .filter(encounterDTO -> encounterDTO.getModificationType() == ModificationType.MODIFIED)
                    .collect(Collectors.toList());
            List<EncounterDTO> deleteCandidates = tournament.getEncounters().stream()
                    .filter(encounterDTO -> encounterDTO.getModificationType() == ModificationType.REMOVED)
                    .collect(Collectors.toList());

            tournamentRepository.addEncounterToTournament(
                    tournament.getId(), mapAnyCollection(updateCandidates, EncounterEntity.class, "EncounterMapping")
            );

            tournamentRepository.removeEncounterFromTournament(
                    tournament.getId(), mapAnyCollection(deleteCandidates, EncounterEntity.class, "EncounterMapping")
            );
        }
        return this.getDetails(tournament.getId(), true);
    }
    //endregion

    //region Data helper methods
    private void denormalizeTournament(TournamentDTO tournamentDTO){
        if (tournamentDTO.getLeague() == null) {
            return;
        }
        // refactor to use repositories instead? would be nicer and faster, but ignores authorization
        SportDTO sportDTO = this.departmentController.getSportByLeagueId(this.session, tournamentDTO.getLeague());
        if(sportDTO.getId() == null){
            return;
        }
        List<LeagueDTO> leagueDTOs = new LinkedList<>();
        for (LeagueDTO leagueDTO : sportDTO.getLeagues()) {
            if(leagueDTO.getId().equals(tournamentDTO.getLeague())){
                leagueDTOs.add(leagueDTO);
            }
        }
        if (leagueDTOs.isEmpty()) {
            return;
        }

        LeagueDTO leagueDTO = leagueDTOs.get(0);
        tournamentDTO.setLeagueName(leagueDTO.getName());
        tournamentDTO.setSportsName(sportDTO.getName());
    }

    private void denormalizeParticipant(ParticipantDTO participantDTO){
        TeamDTO teamData = teamController.getById(this.session, participantDTO.getTeam());
        if(teamData.getResponse() != null){
            return;
        }
        participantDTO.setTeamName(teamData.getName());
        if (participantDTO.getParticipants() == null) {
            participantDTO.setParticipants(new ArrayList<>());
        }
    }

    //endregion

    private void informCoaches(ParticipantDTO participant, TournamentDTO tournament){
        TeamDTO team = teamController.getEntryDetails(session, participant.getTeam());
        List<String> messages = MessageGeneratorService.informCoachInviteToTurnament(team.getTrainers(), tournament);
        int i = 0;
        for (String message :
                messages) {
            messageController.sendMessageToQueue(session, message,team.getTrainers().get(i).getId());
            i++;
        }
    }
}
