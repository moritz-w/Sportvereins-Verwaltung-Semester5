package at.fhv.sportsclub.repository.tournament;

import at.fhv.sportsclub.entity.team.TeamEntity;
import at.fhv.sportsclub.entity.tournament.EncounterEntity;
import at.fhv.sportsclub.entity.tournament.ParticipantEntity;
import at.fhv.sportsclub.entity.tournament.TournamentEntity;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

public interface CustomTournamentRepository {
    void addTeamToTournament(String tournamentId, List<ParticipantEntity> participantEntities);
    void removeTeamFromTournament(String tournamentId, List<ParticipantEntity> participantEntities);
    void addEncounterToTournament(String tournamentId, List<EncounterEntity> encounters);
    void removeEncounterFromTournament(String tournamentId, List<EncounterEntity> encounterEntities);
    List<TournamentEntity> getTournamentByTeamId(List<ObjectId> teamdIds);
    void setTournamentDate(String id, LocalDate date);
    void setTournamentName(String id, String name);
}
