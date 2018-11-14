package at.fhv.sportsclub.security.session;

import at.fhv.sportsclub.model.security.RoleDTO;
import at.fhv.sportsclub.model.security.SessionDTO;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
public abstract class SessionManager<T> {

    private SessionIdService<T> sessionIdService;
    private HashMap<T, List<RoleDTO>> sessionStore;

    public SessionManager(SessionIdService<T> sessionIdService){
        this.sessionIdService = sessionIdService;
        this.sessionStore = new HashMap<>();
    }

    public SessionDTO<T> createNewSession(){
        return null;
    }

    public List<RoleDTO> getSessionMeta(SessionDTO<T> session){
        if (isExpired(session.getExpires()) || !sessionIdService.validateSessionId(session.getSessionId())){
            return new LinkedList<>();
        }
        return sessionStore.getOrDefault(session.getSessionId(), new LinkedList<>());
    }

    private boolean isExpired(Long timestamp){
        return false;
    }
}
