package at.fhv.sportsclub.repository.person;

import at.fhv.sportsclub.entity.person.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class CustomPersonRepositoryImpl implements CustomPersonRepository {

    private MongoOperations mongoOperations;

    @Autowired
    public CustomPersonRepositoryImpl(MongoOperations mongoOperations){
        this.mongoOperations = mongoOperations;
    }
}
