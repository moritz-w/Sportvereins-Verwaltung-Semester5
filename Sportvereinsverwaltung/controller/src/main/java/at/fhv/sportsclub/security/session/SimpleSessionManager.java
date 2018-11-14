package at.fhv.sportsclub.security.session;

import org.springframework.stereotype.Component;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/

public class SimpleSessionManager extends SessionManager<String> {

    public SimpleSessionManager(SessionIdService<String> sessionIdService) {
        super(sessionIdService);
    }
}
