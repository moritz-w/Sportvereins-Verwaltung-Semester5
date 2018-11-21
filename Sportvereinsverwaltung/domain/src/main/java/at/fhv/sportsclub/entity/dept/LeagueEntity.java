package at.fhv.sportsclub.entity.dept;

import at.fhv.sportsclub.entity.CommonEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;

public @Data class LeagueEntity implements CommonEntity {

    @Id
    private String id;
    private String name;
}
