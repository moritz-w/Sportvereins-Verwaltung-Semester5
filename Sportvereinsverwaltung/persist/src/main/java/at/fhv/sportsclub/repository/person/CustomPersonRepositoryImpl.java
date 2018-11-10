package at.fhv.sportsclub.repository.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

@Repository
public class CustomPersonRepositoryImpl implements CustomPersonRepository {

    private MongoOperations mongoOperations;

    @Autowired
    public CustomPersonRepositoryImpl(MongoOperations mongoOperations){
        this.mongoOperations = mongoOperations;
    }
}
