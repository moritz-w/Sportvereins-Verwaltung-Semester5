package at.fhv.sportsclub.entity.tournament;

import at.fhv.sportsclub.entity.CommonEntity;
import lombok.Data;
import org.bson.types.ObjectId;

/*
      Created: 06.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
public @Data class SquadMemberEntity implements CommonEntity {
    private String id;
    private ObjectId member;
    private boolean participating;
}
