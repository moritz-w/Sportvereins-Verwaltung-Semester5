package at.fhv.sportsclub.ejb;

import at.fhv.sportsclub.ejb.interfaces.ITournamentController;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;

import javax.ejb.Stateless;
import java.rmi.RemoteException;

/*
      Created: 11.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Stateless
public class TournamentControllerBean implements ITournamentController {

    private final at.fhv.sportsclub.controller.interfaces.ITournamentController tournamentController;

    public TournamentControllerBean() {
        SpringContextBean contextBean = SpringContextBeanFactory.getInstance();
        this.tournamentController = contextBean.getTournamentController();
    }

    @Override
    public ListWrapper<TournamentDTO> getAllEntries(SessionDTO session) {
        try {
            return tournamentController.getAllEntries(session);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TournamentDTO getEntryDetails(SessionDTO session, String id) {
        try {
            tournamentController.getEntryDetails(session, id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TournamentDTO getById(SessionDTO session, String id) {
        try {
            return tournamentController.getById(session, id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ListWrapper<TournamentDTO> getTournamentByTrainerId(SessionDTO session, String personId) {
        try {
            return tournamentController.getTournamentByTrainerId(session, personId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TournamentDTO saveOrUpdateEntry(SessionDTO session, TournamentDTO tournament) {
        try {
            tournamentController.saveOrUpdateEntry(session, tournament);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
