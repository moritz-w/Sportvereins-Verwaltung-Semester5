package at.fhv.sportsclub.model.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@AllArgsConstructor
public @Data class SessionDTO<T> implements Serializable {
    private T sessionId;
    private Long expires;
}
