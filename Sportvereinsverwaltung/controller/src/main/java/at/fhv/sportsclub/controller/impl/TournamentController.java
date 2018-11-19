package at.fhv.sportsclub.controller.impl;

import at.fhv.sportsclub.controller.common.CommonController;
import at.fhv.sportsclub.controller.interfaces.ITournamentController;
import at.fhv.sportsclub.entity.team.TeamEntity;
import at.fhv.sportsclub.entity.tournament.TournamentEntity;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.team.TeamDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;
import at.fhv.sportsclub.repository.tournament.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.ArrayList;

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
    public ResponseMessageDTO saveOrUpdateEntry(TournamentDTO tournamentDTO) throws RemoteException {
        return this.saveOrUpdate(tournamentDTO);
    }

    @Override
    public ResponseMessageDTO addTeamToTurnament(TournamentDTO tournamentDTO, TeamDTO teamDTO) throws RemoteException {



        TournamentEntity tournament = this.map(tournamentDTO, TournamentEntity.class, "");
        TeamEntity team = this.map(teamDTO, TeamEntity.class, "TeamDTOMappingFull");

        this.tournamentReposetory.addTeamToTournament(tournament, team);
        return new ResponseMessageDTO(/*Iwie muss das ding initialisiert werden*/);
    }




}
