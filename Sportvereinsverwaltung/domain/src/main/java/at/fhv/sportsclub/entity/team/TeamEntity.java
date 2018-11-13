package at.fhv.sportsclub.entity.team;

import at.fhv.sportsclub.entity.CommonEntity;
import at.fhv.sportsclub.entity.dept.LeagueEntity;
import at.fhv.sportsclub.entity.person.PersonEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Team")
public @Data class TeamEntity implements CommonEntity {

    @Id
    private String id;

    private String name, type;
    @DBRef
    private List<PersonEntity> members;
    @DBRef
    private List<PersonEntity> trainers;
    @DBRef
    private LeagueEntity league;
}
