package at.fhv.sportsclub.controller;

import at.fhv.sportsclub.controller.common.CommonController;
import at.fhv.sportsclub.entity.person.PersonEntity;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.repository.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonController extends CommonController<PersonDTO, PersonEntity, PersonRepository> {

    private PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository repository) {
        super(repository, PersonDTO.class, PersonEntity.class);
        this.personRepository = repository;
    }

    @Override
    protected String getId(PersonEntity entity) {
        return entity.getId();
    }
}
