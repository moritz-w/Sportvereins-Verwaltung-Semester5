package at.fhv.sportsclub.controller.interfaces;

import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITournamentController extends Remote {
    ListWrapper<TournamentDTO> getAllEntries(SessionDTO session) throws RemoteException;
    TournamentDTO getEntryDetails(SessionDTO session, String id) throws RemoteException;
    TournamentDTO getById(SessionDTO session, String id) throws RemoteException;
    TournamentDTO saveOrUpdateEntry(SessionDTO session, TournamentDTO tournament) throws RemoteException;
}
