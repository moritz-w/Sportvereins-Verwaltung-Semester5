package at.fhv.sportsclub.entity.dept;

import lombok.Data;
import org.springframework.data.annotation.Id;

public @Data class LeagueEntity {

    @Id
    private String id;

    private String name;
}
