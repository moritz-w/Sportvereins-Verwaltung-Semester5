package at.fhv.sportsclub.repository.team;

import at.fhv.sportsclub.entity.team.TeamEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
      Created: 02.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Repository
public class CustomTeamRepositoryImpl implements CustomTeamRepository {


    private static final Logger logger = Logger.getRootLogger();
    private MongoOperations mongoOperations;

    @Autowired
    public CustomTeamRepositoryImpl(MongoOperations mongoOperations){
        this.mongoOperations = mongoOperations;
    }
}
