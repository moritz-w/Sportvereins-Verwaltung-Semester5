package at.fhv.sportsclub.security.session;

import at.fhv.sportsclub.model.security.UserDetails;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
public interface SessionIdService<T> {
    T generateSessionId(UserDetails userDetails);
    boolean validateSessionId(T sessionId, UserDetails userDetails);
}
