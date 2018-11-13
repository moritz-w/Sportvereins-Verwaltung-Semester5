package at.fhv.sportsclub.entity.tournament;

import at.fhv.sportsclub.entity.CommonEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalTime;

public @Data class EncounterEntity implements CommonEntity {

    @Id
    private String id;

    private LocalDate date;
    private LocalTime time;
    private ResultEntity homeTeam, guestTeam;
}
