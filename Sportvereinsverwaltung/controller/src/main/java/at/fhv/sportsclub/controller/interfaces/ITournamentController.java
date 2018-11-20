package at.fhv.sportsclub.controller.interfaces;

import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.tournament.EncounterDTO;
import at.fhv.sportsclub.model.tournament.ParticipantDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ITournamentController extends Remote {
    ArrayList<TournamentDTO> getAllEntries() throws RemoteException;
    ListWrapper<ParticipantDTO> addTeamsToTournament(TournamentDTO tournamentDTO, List<String> teamId) throws RemoteException;
    ResponseMessageDTO addEncountersToTournament(String tournamentId, List<EncounterDTO> encounters);
}
