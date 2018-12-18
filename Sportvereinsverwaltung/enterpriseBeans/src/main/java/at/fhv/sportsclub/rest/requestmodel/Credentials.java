package at.fhv.sportsclub.rest.requestmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
      Created: 12.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@AllArgsConstructor
@NoArgsConstructor
public @Data class Credentials {
    private String user;
    private String password;
}
