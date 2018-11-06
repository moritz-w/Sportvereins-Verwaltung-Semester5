package at.fhv.sportsclub.entity.dept;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

public @Data class SportEntity {

    @Id
    private String id;

    private String name;
    @DBRef
    private List<LeagueEntity> leagues;
}