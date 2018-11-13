package at.fhv.sportsclub.controller;

import at.fhv.sportsclub.controller.common.CommonController;
import at.fhv.sportsclub.controller.interfaces.IPersonController;
import at.fhv.sportsclub.entity.person.PersonEntity;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.repository.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Scope("prototype")
public class PersonController extends CommonController<PersonDTO, PersonEntity, PersonRepository> implements IPersonController {

    private PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository repository) {
        super(repository, PersonDTO.class, PersonEntity.class);
        this.personRepository = repository;
    }

    //region RMI wrapper methods
    @Override
    public ArrayList<PersonDTO> getAllEntries() {
        return new ArrayList<>(this.getAll());
    }

    @Override
    public ResponseMessageDTO saveOrUpdateEntry(PersonDTO personDTO) {
        return this.saveOrUpdate(personDTO);
    }
    //endregion
}
