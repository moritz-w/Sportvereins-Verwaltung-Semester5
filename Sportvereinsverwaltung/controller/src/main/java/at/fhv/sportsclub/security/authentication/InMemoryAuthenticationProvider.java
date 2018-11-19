package at.fhv.sportsclub.security.authentication;

import at.fhv.sportsclub.model.security.UserAuthentication;

import java.util.Arrays;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
public class InMemoryAuthenticationProvider implements AuthenticationProvider {
    @Override
    public boolean authenticate(UserAuthentication authentication) {
        return Arrays.equals(authentication.getId().toCharArray(), authentication.getCredentials());
    }
}
