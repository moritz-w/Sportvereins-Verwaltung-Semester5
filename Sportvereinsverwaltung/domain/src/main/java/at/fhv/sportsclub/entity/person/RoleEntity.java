package at.fhv.sportsclub.entity.person;

import at.fhv.sportsclub.entity.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/*
      Created: 18.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Document(collection = "Role")
@AllArgsConstructor
public @Data class RoleEntity implements CommonEntity {

    @Id
    private String id;
    private String name;
    private List<PrivilegeEntity> privileges;

    public RoleEntity() {}
}
