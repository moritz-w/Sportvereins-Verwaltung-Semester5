package at.fhv.sportsclub.rest;

import at.fhv.sportsclub.ejb.SpringContextBean;
import at.fhv.sportsclub.ejb.SpringContextBeanFactory;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.model.security.SessionDTO;

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
@Path("/person")
public class PersonServiceBean {

    private at.fhv.sportsclub.controller.interfaces.IPersonController personController;

    public PersonServiceBean(){
        SpringContextBean contextBean = SpringContextBeanFactory.getInstance();
        this.personController = contextBean.getPersonController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ListWrapper<PersonDTO> getAllEntries(@CookieParam("sessionId") String sessionId) {
        try {
            SessionDTO<String> session = new SessionDTO<>();
            session.setSessionId(sessionId);
            return this.personController.getAllEntries(session);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDTO getEntryDetails(@CookieParam("sessionId") String sessionId, @PathParam("id") String id) {
        try {
            SessionDTO<String> session = new SessionDTO<>();
            session.setSessionId(sessionId);
            return this.personController.getEntryDetails(session, id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseMessageDTO saveOrUpdateEntry(@CookieParam("sessionId") String sessionId, PersonDTO personDTO) {
        try {
            SessionDTO<String> session = new SessionDTO<>();
            session.setSessionId(sessionId);
            return this.personController.saveOrUpdateEntry(session, personDTO);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
