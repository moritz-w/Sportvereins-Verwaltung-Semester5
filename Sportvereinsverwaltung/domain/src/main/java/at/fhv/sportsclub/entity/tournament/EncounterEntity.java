package at.fhv.sportsclub.entity.tournament;

import at.fhv.sportsclub.entity.CommonEntity;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.time.LocalTime;

public @Data class EncounterEntity implements CommonEntity {

    @Id
    private String id;

    private LocalDate date;
    private LocalTime time;
    private ObjectId homeTeam;
    private ObjectId guestTeam;
    private int homePoints;
    private int guestPoints;
}
