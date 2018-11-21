package at.fhv.sportsclub.repository.tournament;

import at.fhv.sportsclub.entity.team.TeamEntity;
import at.fhv.sportsclub.entity.tournament.EncounterEntity;
import at.fhv.sportsclub.entity.tournament.ParticipantEntity;
import at.fhv.sportsclub.entity.tournament.TournamentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomTournamentRepositoryImpl implements CustomTournamentRepository {

    private MongoOperations mongoOperations;

    @Autowired
    public CustomTournamentRepositoryImpl(MongoOperations mongoOperations){
        this.mongoOperations = mongoOperations;
    }


    @Override
    public void addTeamToTournament(String tournamentId, List<ParticipantEntity> participantEntities) {

    }

    @Override
    public void addEncounterToTournament(String tournamentId, List<EncounterEntity> encounters){
    }

    @Override
    public void addPersonsToParticipant(String participantId, List<String> participants){
    }
}
