package at.fhv.sportsclub.security.authentication;

import at.fhv.sportsclub.model.security.UserDetails;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
public interface UserDetailsProvider {
    UserDetails getUserDetails(String userId);
}
