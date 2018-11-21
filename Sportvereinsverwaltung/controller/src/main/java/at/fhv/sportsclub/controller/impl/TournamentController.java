package at.fhv.sportsclub.controller.impl;

import at.fhv.sportsclub.controller.common.CommonController;
import at.fhv.sportsclub.controller.interfaces.ITournamentController;
import at.fhv.sportsclub.entity.tournament.TournamentEntity;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.LeagueDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.team.TeamDTO;
import at.fhv.sportsclub.model.tournament.EncounterDTO;
import at.fhv.sportsclub.model.tournament.ParticipantDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;
import at.fhv.sportsclub.repository.tournament.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Scope("prototype")
public class TournamentController extends CommonController<TournamentDTO, TournamentEntity, TournamentRepository> implements ITournamentController {

    private final TournamentRepository tournamentRepository;
    private final DepartmentController departmentController;
    private final TeamController teamController;

    private SessionDTO session;

    @Autowired
    public TournamentController(
            TournamentRepository repository,
            DepartmentController departmentController,
            TeamController teamController
    ) {
        super(repository, TournamentDTO.class, TournamentEntity.class);
        this.tournamentRepository = repository;
        this.departmentController = departmentController;
        this.teamController = teamController;
    }
    // region RMI wrapper methods
    @Override
    public ListWrapper<TournamentDTO> getAllEntries(SessionDTO session) throws RemoteException {
        return new ListWrapper<>(new ArrayList<>(this.getAll()), null);
    }

    @Override
    public TournamentDTO getByIdFull(SessionDTO session, String id) throws RemoteException {
        return this.getDetails(id, true);
    }

    @Override
    public TournamentDTO getById(SessionDTO session, String id) throws RemoteException{
        return this.getDetails(id, false);
    }

    @Override
    public ResponseMessageDTO addEncountersToTournament(SessionDTO session, String tournamentId, List<EncounterDTO> encounters) throws RemoteException{
        /*
         *  add encounters to given tournament ID
         */
        return null;
    }

    /**
     * For subsequently adding teams to a tournament
     * @param tournamentDTO
     * @param teamIds
     * @return
     * @throws RemoteException
     */
    @Override
    public TournamentDTO addParticipantsToTournament(SessionDTO session, TournamentDTO tournamentDTO, List<String> teamIds) throws RemoteException {
        this.session = session;
        tournamentDTO.setEncounters(new LinkedList<>());        // explicitly remove encounters
        TournamentDTO result = new TournamentDTO();

        ResponseMessageDTO responseMessageDTO = validateDto(tournamentDTO);
        if (!responseMessageDTO.isValidated()) {
            result.setResponse(responseMessageDTO);
            return result;
        }
        if (tournamentDTO.getId() == null){
            denormalizeTournament(tournamentDTO);
            // create participants and insert to tournament, then save
            result.setTeams(createParticipants(teamIds));
            ResponseMessageDTO saveResponse = this.saveOrUpdate(result);
            if (saveResponse.isSuccess()) {
                return this.getDetails(saveResponse.getContextId(), true);
            } else {
                return rejectRequest(saveResponse.getInfoMessage());
            }
        } else {
            // update participants by calling corresponding repo method
            List<ParticipantDTO> newParticipants = createParticipants(teamIds);
        }
        return null;
    }

    public ResponseMessageDTO removeEncounter(String tournamentId, String encounterId){
        return null;
    }
    //endregion

    //region Data helper methods
    private void denormalizeTournament(TournamentDTO tournamentDTO){
        if (tournamentDTO.getLeague() == null) {
            return;
        }
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
    }

    private List<ParticipantDTO> createParticipants(List<String> teamIds){
        List<ParticipantDTO> participants = new ArrayList<>();
        for (String teamId : teamIds) {
            ParticipantDTO newParticipant = new ParticipantDTO();
            newParticipant.setTeam(teamId);
            newParticipant.setParticipants(new ArrayList<>());
            denormalizeParticipant(newParticipant);
            participants.add(newParticipant);
        }
        return participants;
    }

    //endregion

}
