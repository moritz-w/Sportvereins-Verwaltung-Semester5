package at.fhv.sportsclub.repository.tournament;

import at.fhv.sportsclub.entity.team.TeamEntity;
import at.fhv.sportsclub.entity.tournament.EncounterEntity;
import at.fhv.sportsclub.entity.tournament.ParticipantEntity;
import at.fhv.sportsclub.entity.tournament.TournamentEntity;

import java.util.List;

public interface CustomTournamentRepository {
    void addTeamToTournament(String tournamentId, List<ParticipantEntity> participantEntities);
    void addEncounterToTournament(String tournamentId, List<EncounterEntity> encounters);
    void addPersonsToParticipant(String participantId, List<String> participants);
}
