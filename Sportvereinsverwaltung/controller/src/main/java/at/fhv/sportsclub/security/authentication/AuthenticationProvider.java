package at.fhv.sportsclub.security.authentication;

import at.fhv.sportsclub.model.security.UserAuthentication;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/

public interface AuthenticationProvider {
    boolean authenticate(UserAuthentication authentication);
}
