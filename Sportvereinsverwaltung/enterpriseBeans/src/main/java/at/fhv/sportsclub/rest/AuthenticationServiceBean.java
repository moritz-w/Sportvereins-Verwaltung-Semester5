package at.fhv.sportsclub.rest;

import at.fhv.sportsclub.ejb.SpringContextBean;
import at.fhv.sportsclub.ejb.SpringContextBeanFactory;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.rest.requestmodel.Credentials;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.rmi.RemoteException;

/*
      Created: 11.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Path("/auth")
public class AuthenticationServiceBean {

    private static final Logger logger = Logger.getRootLogger();
    private static at.fhv.sportsclub.security.authentication.IAuthenticationController authenticationController = null;

    public AuthenticationServiceBean(){
        SpringContextBean contextBean = SpringContextBeanFactory.getInstance();
        if (authenticationController == null){
            authenticationController = contextBean.getAuthenticationController();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticate(Credentials credentials) {
        if(credentials == null || credentials.getUser().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            SessionDTO<String> session = authenticationController.authenticate(credentials.getUser(), credentials.getPassword().toCharArray());
            return Response.ok().cookie(new NewCookie("sessionId", session.getSessionId())).build();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @GET
    public Response logout(@CookieParam("sessionId") String sessionId) {
        if(sessionId == null ||sessionId.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            SessionDTO<String> session = new SessionDTO<>();
            session.setSessionId(sessionId);
            authenticationController.logout(session);
            return Response.ok().build();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path("anonymous")
    public Response anonymousLogin(){
        try {
            SessionDTO<String> session = authenticationController.authenticate("appUser", "appUser".toCharArray());
            return Response.ok().cookie(new NewCookie("sessionId", session.getSessionId())).build();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @AllArgsConstructor
    public @Data class Status {
        private String status;
    }

    @GET
    @Path("status")
    public Status status(){
        return new Status("Wo bleibt mein Big King XXL mit Chilli-Cheese-Nuggets?");
    }
}
