package at.fhv.sportsclub.entity.dept;

import at.fhv.sportsclub.entity.CommonEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

public @Data class SportEntity implements CommonEntity {

    @Id
    private String id;

    private String name;
    private List<LeagueEntity> leagues;
}
