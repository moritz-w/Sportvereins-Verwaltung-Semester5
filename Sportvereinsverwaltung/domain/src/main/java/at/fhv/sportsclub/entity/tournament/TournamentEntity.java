package at.fhv.sportsclub.entity.tournament;

import at.fhv.sportsclub.entity.CommonEntity;
import at.fhv.sportsclub.entity.dept.LeagueEntity;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Tournament")
public @Data class TournamentEntity implements CommonEntity {

    @Id
    private String id;
    private String name;


    private ObjectId league;

    private List<EncounterEntity> encounters;
    private List<ParticipantEntity> teams;
}
