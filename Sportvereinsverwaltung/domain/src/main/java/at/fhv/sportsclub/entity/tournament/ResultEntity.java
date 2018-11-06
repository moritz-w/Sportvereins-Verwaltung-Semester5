package at.fhv.sportsclub.entity.tournament;

import at.fhv.sportsclub.entity.team.TeamEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public @Data class ResultEntity {

    @Id
    private String id;

    @DBRef
    private TeamEntity team;
    private Integer points;
}