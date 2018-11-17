package at.fhv.sportsclub.security.authentication;

import at.fhv.sportsclub.model.security.UserDetails;
import at.fhv.sportsclub.repository.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Component
public class DataRepositoryDetailsProvider implements UserDetailsProvider {

    private final PersonRepository repository;

    @Autowired
    public DataRepositoryDetailsProvider(PersonRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails getUserDetails(String userId) {
        return null;
    }
}
