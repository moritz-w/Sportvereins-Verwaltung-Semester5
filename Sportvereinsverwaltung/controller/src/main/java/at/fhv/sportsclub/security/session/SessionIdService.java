package at.fhv.sportsclub.security.session;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
public interface SessionIdService<T> {
    T generateSessionId();
    boolean validateSessionId(T sessionId);
}
