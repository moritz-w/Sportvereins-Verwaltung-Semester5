package at.fhv.sportsclub.security.session;

import org.springframework.stereotype.Component;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Component
public class SimpleSessionIdService implements SessionIdService<String> {

    @Override
    public String generateSessionId() {
        return "000";
    }

    @Override
    public boolean validateSessionId(String sessionId) {
        return true;
    }
}
