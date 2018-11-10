package at.fhv.sportsclub.entity.tournament;

import at.fhv.sportsclub.entity.CommonEntity;
import at.fhv.sportsclub.entity.person.PersonEntity;
import at.fhv.sportsclub.entity.team.TeamEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

public @Data class ParticipantEntity implements CommonEntity {

    @Id
    private String id;
    @DBRef
    private TeamEntity team;
    private String teamName;
    @DBRef
    private List<PersonEntity> participants;
}
