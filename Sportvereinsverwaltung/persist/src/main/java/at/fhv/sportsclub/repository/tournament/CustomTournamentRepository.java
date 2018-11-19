package at.fhv.sportsclub.repository.tournament;

import at.fhv.sportsclub.entity.team.TeamEntity;
import at.fhv.sportsclub.entity.tournament.TournamentEntity;

public interface CustomTournamentRepository {


    void addTeamToTournament(TournamentEntity tournament, TeamEntity team);

}
