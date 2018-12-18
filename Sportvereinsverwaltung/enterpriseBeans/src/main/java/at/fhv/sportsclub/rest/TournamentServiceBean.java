package at.fhv.sportsclub.rest;

import at.fhv.sportsclub.ejb.SpringContextBean;
import at.fhv.sportsclub.ejb.SpringContextBeanFactory;
import at.fhv.sportsclub.ejb.interfaces.ITournamentController;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.tournament.TournamentDTO;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.rmi.RemoteException;

/*
      Created: 11.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Path("/tournament")
public class TournamentServiceBean {

    private final at.fhv.sportsclub.controller.interfaces.ITournamentController tournamentController;

    public TournamentServiceBean() {
        SpringContextBean contextBean = SpringContextBeanFactory.getInstance();
        this.tournamentController = contextBean.getTournamentController();
    }

    @GET
    public ListWrapper<TournamentDTO> getAllEntries(@CookieParam("sessionId") String sessionId) {
        try {
            SessionDTO<String> session = new SessionDTO<>();
            session.setSessionId(sessionId);
            return tournamentController.getAllEntries(session);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GET
    @Path("{id}")
    public TournamentDTO getEntryDetails(@CookieParam("sessionId") String sessionId, @PathParam("id") String id) {
        try {
            SessionDTO<String> session = new SessionDTO<>();
            session.setSessionId(sessionId);
            tournamentController.getEntryDetails(session, id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TournamentDTO getById(SessionDTO session, String id) {
        try {
            return tournamentController.getById(session, id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ListWrapper<TournamentDTO> getTournamentByTrainerId(SessionDTO session, String personId) {
        try {
            return tournamentController.getTournamentByTrainerId(session, personId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TournamentDTO saveOrUpdateEntry(SessionDTO session, TournamentDTO tournament) {
        try {
            tournamentController.saveOrUpdateEntry(session, tournament);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
