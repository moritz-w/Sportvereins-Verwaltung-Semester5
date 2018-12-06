package at.fhv.sportsclub.model.tournament;

import at.fhv.sportsclub.model.common.IDTO;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import lombok.Data;

/*
      Created: 06.12.2018
      Author: Moritz W.
      Co-Authors: 
*/

public @Data class SquadMemberDTO implements IDTO {
    private PersonDTO member;
    private boolean participating;
    private ResponseMessageDTO response;
}
