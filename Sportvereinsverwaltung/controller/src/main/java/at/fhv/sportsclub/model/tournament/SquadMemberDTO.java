package at.fhv.sportsclub.model.tournament;

import at.fhv.sportsclub.model.common.IDTO;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/*
      Created: 06.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@AllArgsConstructor
public @Data class SquadMemberDTO implements Serializable, IDTO {
    private PersonDTO member;
    private boolean participating;
    private ResponseMessageDTO response;
}
