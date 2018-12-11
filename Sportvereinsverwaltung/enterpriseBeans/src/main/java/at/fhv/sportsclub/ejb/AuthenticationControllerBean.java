package at.fhv.sportsclub.ejb;

import at.fhv.sportsclub.ejb.interfaces.IAuthenticationController;
import at.fhv.sportsclub.model.security.SessionDTO;

import javax.ejb.Singleton;
import java.rmi.RemoteException;

/*
      Created: 11.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Singleton
public class AuthenticationControllerBean implements IAuthenticationController {

    private final at.fhv.sportsclub.security.authentication.IAuthenticationController authenticationController;

    public AuthenticationControllerBean(){
        SpringContextBean contextBean = SpringContextBeanFactory.getInstance();
        this.authenticationController = contextBean.getAuthenticationController();
    }

    @Override
    public SessionDTO authenticate(String userId, char[] password) {
        try {
            return authenticationController.authenticate(userId, password);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void logout(SessionDTO session) {
        try {
            authenticationController.logout(session);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
