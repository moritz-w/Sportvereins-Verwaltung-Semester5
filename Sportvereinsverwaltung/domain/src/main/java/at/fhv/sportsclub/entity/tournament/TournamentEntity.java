package at.fhv.sportsclub.entity.tournament;

import at.fhv.sportsclub.entity.dept.LeagueEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Tournament")
public @Data class TournamentEntity {

    @Id
    private String id;
    private String name; //leagueName, sportsName;

    @DBRef
    private LeagueEntity league;

    @DBRef
    private List<EncounterEntity> encounters;

    @DBRef
    private List<ParticipantEntity> teams;
}
