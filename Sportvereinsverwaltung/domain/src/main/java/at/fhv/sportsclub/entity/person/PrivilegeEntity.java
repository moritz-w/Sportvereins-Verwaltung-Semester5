package at.fhv.sportsclub.entity.person;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/*
      Created: 18.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@AllArgsConstructor
public @Data class PrivilegeEntity {
    private String domain;
    private List<String> accessLevels;

    public PrivilegeEntity() {}
}
