package at.fhv.sportsclub.repository.tournament;

import at.fhv.sportsclub.entity.tournament.ParticipantEntity;
import at.fhv.sportsclub.entity.tournament.TournamentEntity;
import at.fhv.sportsclub.repository.CommonRepository;

import java.util.List;

public interface TournamentRepository extends CommonRepository<TournamentEntity, String>, CustomTournamentRepository  {
}
