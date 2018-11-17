package at.fhv.sportsclub.security.authorization;

import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.security.authentication.AuthenticationController;
import at.fhv.sportsclub.security.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
      Created: 13.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Component
public class AuthorizationController {

    private final SessionManager sessionManager;

    @Autowired
    public AuthorizationController(SessionManager sessionManager){
        this.sessionManager = sessionManager;
    }

    public void checkPermissions(SessionDTO session){

    }
}
