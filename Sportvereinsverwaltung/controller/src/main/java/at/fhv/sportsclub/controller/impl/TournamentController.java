package at.fhv.sportsclub.controller.impl;

import at.fhv.sportsclub.controller.common.CommonController;
import at.fhv.sportsclub.controller.interfaces.ITournamentController;
import at.fhv.sportsclub.entity.team.TeamEntity;
import at.fhv.sportsclub.entity.tournament.TournamentEntity;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.team.TeamDTO;
import at.fhv.sportsclub.model.tournament.EncounterDTO;
import at.fhv.sportsclub.model.tournament.ParticipantDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;
import at.fhv.sportsclub.repository.tournament.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Scope("prototype")
public class TournamentController extends CommonController<TournamentDTO, TournamentEntity, TournamentRepository> implements ITournamentController {

    private TournamentRepository tournamentReposetory;

    @Autowired
    public TournamentController(TournamentRepository repository) {
        super(repository, TournamentDTO.class, TournamentEntity.class);
        this.tournamentReposetory = repository;
    }

    @Override
    public ArrayList<TournamentDTO> getAllEntries() throws RemoteException {
        return new ArrayList<>(this.getAll());
    }

    @Override
    public ResponseMessageDTO addEncountersToTournament(String tournamentId, List<EncounterDTO> encounters){
        /*
         *  add encounters to given tournament ID
         */
        return null;
    }

    /**
     * For subsequently adding teams to a tournament
     * @param tournamentDTO
     * @param teamId
     * @return
     * @throws RemoteException
     */
    @Override
    public ListWrapper<ParticipantDTO> addTeamsToTournament(TournamentDTO tournamentDTO, List<String> teamId) throws RemoteException {
        tournamentDTO.setEncounters(new LinkedList<>());        // explicitly remove encounters
        tournamentDTO.setTeams(new LinkedList<>());             // "
        // denormalize tournament data
        /*
            replaces the saveOrUpdate method for the client, tournament dto only needs flat properties (no teams no encounters)
            iterate over list of teamIds and resolve teams from TeamController with light mapping (full false)
            Denormalize data from team to ParticipantDTO and push data to array of tournament ID
            Return participantDTOs that where just created (including Object Ids)
         */
        this.saveOrUpdate(tournamentDTO);
        return null;
    }

    public ResponseMessageDTO removeEncounter(String tournamentId, String encounterId){
        return null;
    }

}
