package at.fhv.sportsclub.security.authentication;

import at.fhv.sportsclub.controller.interfaces.IAuthenticationController;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.security.UserAuthentication;
import at.fhv.sportsclub.security.session.SessionManager;

import java.util.Iterator;
import java.util.List;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/

public class AuthenticationController implements IAuthenticationController {

    private SessionManager sessionManager;
    private List<AuthenticationProvider> authenticationProviderList;

    public AuthenticationController(List<AuthenticationProvider> authenticationProviderList, SessionManager sessionManager){
        this.authenticationProviderList = authenticationProviderList;
        this.sessionManager = sessionManager;
    }

    public SessionDTO tryAuthentication(UserAuthentication authentication){
        Iterator<AuthenticationProvider> providerIterator = authenticationProviderList.iterator();
        boolean authenticated;
        SessionDTO session = new SessionDTO();
        while(providerIterator.hasNext()){
            authenticated = providerIterator.next().authenticate(authentication);
            if (authenticated){
                session = sessionManager.createNewSession();
            }
        }
        afterAuthCleanup(authentication);
        return session;
    }

    protected void afterAuthCleanup(UserAuthentication authentication){
        authentication.setCredentials("######");
        authentication.setId("######");
    }

    @Override
    public SessionDTO authenticate(String userId, String password) {
        return tryAuthentication(new UserAuthentication(userId, password));
    }
}
