package at.fhv.sportsclub.person;

import at.fhv.sportsclub.common.Controller;
import at.fhv.sportsclub.entity.person.PersonEntity;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.repository.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonController implements Controller<PersonDTO> {

    private PersonRepository repository;

    @Autowired
    public PersonController(PersonRepository repository){
        this.repository = repository;
    }

    @Override
    public List<PersonDTO> getAll() {
        return null;
    }

    @Override
    public boolean saveOrUpdate(PersonDTO dto) {
        return false;
    }

    /*
        TODO:
            - mapping from/to DTOs
            - RMI connection
            - Async
            - method prototypes
            - validation
     */
}
