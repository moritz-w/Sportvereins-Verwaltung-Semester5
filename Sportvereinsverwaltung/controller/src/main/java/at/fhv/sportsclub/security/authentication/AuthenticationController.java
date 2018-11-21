package at.fhv.sportsclub.security.authentication;

import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.security.UserAuthentication;
import at.fhv.sportsclub.model.security.UserDetails;
import at.fhv.sportsclub.security.session.SessionManager;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/

public class AuthenticationController implements IAuthenticationController {

    private final UserDetailsProvider userDetailsProvider;
    private SessionManager sessionManager;
    private List<AuthenticationProvider> authenticationProviderList;

    public AuthenticationController(List<AuthenticationProvider> authenticationProviderList,
                                    UserDetailsProvider userDetailsProvider, SessionManager sessionManager){
        this.authenticationProviderList = authenticationProviderList;
        this.sessionManager = sessionManager;
        this.userDetailsProvider = userDetailsProvider;
    }

    /**
     * Use the given authentication providers to authenticate a user by his/her credentials
     * @param authentication User authentication including the credentials and user identification
     * @return null, if the authentication failed. Otherwise a validate Session object is returned
     */
    private SessionDTO tryAuthentication(UserAuthentication authentication){
        Iterator<AuthenticationProvider> providerIterator = authenticationProviderList.iterator();
        boolean authenticated;
        SessionDTO session = null;
        while(providerIterator.hasNext() && session == null){
            authenticated = providerIterator.next().authenticate(authentication);
            if (authenticated){
                UserDetails userDetails = userDetailsProvider.getUserDetails(authentication.getId());
                session = sessionManager.createNewSession(userDetails);
            }
        }
        afterAuthCleanup(authentication);
        return session;
    }

    protected void afterAuthCleanup(UserAuthentication authentication){
        authentication.clearCredentials();
        authentication.setId("");
    }

    //region RMI wrapper methods
    @Override
    public SessionDTO authenticate(String userId, char[] password) throws RemoteException {
        return tryAuthentication(new UserAuthentication(userId, password));
    }

    @Override
    public void logout(SessionDTO session) throws RemoteException {
        sessionManager.invalidate(session);
    }
    //endregion
}
