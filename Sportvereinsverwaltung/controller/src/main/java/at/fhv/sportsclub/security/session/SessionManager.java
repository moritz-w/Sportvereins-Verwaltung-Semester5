package at.fhv.sportsclub.security.session;

import at.fhv.sportsclub.model.security.RoleDTO;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.security.UserDetails;
import at.fhv.sportsclub.security.exception.SessionInvalidException;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
public abstract class SessionManager<T> {

    private static final Long sessionTime = 360000L;

    private SessionIdService<T> sessionIdService;
    private HashMap<T, Session> sessionStore;

    public SessionManager(SessionIdService<T> sessionIdService){
        this.sessionIdService = sessionIdService;
        this.sessionStore = new HashMap<>();
    }

    public SessionDTO<T> createNewSession(UserDetails userDetails){
        if(userDetails == null){
            // TODO message
        }
        userDetails.setNonce(getNonce());
        T generatedSessionId = sessionIdService.generateSessionId(userDetails);

        Long expirationTime = System.currentTimeMillis() / 1000L + sessionTime;
        sessionStore.put(generatedSessionId, new Session(userDetails, expirationTime));
        return new SessionDTO<>(generatedSessionId, expirationTime);
    }

    public SessionDTO<T> refreshSession(SessionDTO<T> session){
        return null;
    }

    public void invalidate(SessionDTO<T> session){
        sessionStore.remove(session.getSessionId());
    }

    public List<RoleDTO> getAccessRolesBySession(SessionDTO<T> session) throws SessionInvalidException {
        String message;
        if(!(message = validateSession(session)).isEmpty()){
            throw new SessionInvalidException(message);
        }
        return sessionStore.get(session.getSessionId()).getUserDetails().getRoles();
    }

    private String validateSession(SessionDTO<T> session){
        Session validSession = sessionStore.get(session.getSessionId());
        if (validSession == null){
            return "Session ID invalid";
        }
        UserDetails userDetails = validSession.getUserDetails();
        if (!sessionIdService.validateSessionId(session.getSessionId(), userDetails)){
            return "Session ID invalid";
        }
        if(isExpired(validSession.getExpires())){
            sessionStore.remove(session.getSessionId());
            return "Session expired";
        }
        return "";
    }

    private boolean isExpired(Long timestamp){
        return (System.currentTimeMillis()/1000L > timestamp);
    }

    private String getNonce(){
        SecureRandom sec = new SecureRandom();
        byte[] values = new byte[20];
        sec.nextBytes(values);
        return new String(values, StandardCharsets.UTF_8);
    }
}
