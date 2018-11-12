package at.fhv.sportsclub.controller;

import at.fhv.sportsclub.controller.common.CommonController;
import at.fhv.sportsclub.controller.interfaces.ITournamentController;
import at.fhv.sportsclub.entity.tournament.TournamentEntity;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;
import at.fhv.sportsclub.repository.tournament.ITournamentReposetory;
import org.springframework.beans.factory.annotation.Autowired;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class TournamentController extends CommonController<TournamentDTO, TournamentEntity, ITournamentReposetory> implements ITournamentController {

    private ITournamentReposetory tournamentReposetory;

    @Autowired
    public TournamentController(ITournamentReposetory repository) {
        super(repository, TournamentDTO.class, TournamentEntity.class);
        this.tournamentReposetory = repository;
    }

    @Override
    public ArrayList<TournamentDTO> getAllEntries() throws RemoteException {
        return null;
    }

    @Override
    public ResponseMessageDTO saveOrUpdateEntry(TournamentDTO tournamentDTO) throws RemoteException {
        return null;
    }
}
