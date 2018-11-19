package at.fhv.sportsclub.security.session;

import at.fhv.sportsclub.model.security.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/*
      Created: 15.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@AllArgsConstructor
public @Data class Session {
    private UserDetails userDetails;
    private Long expires;
}
