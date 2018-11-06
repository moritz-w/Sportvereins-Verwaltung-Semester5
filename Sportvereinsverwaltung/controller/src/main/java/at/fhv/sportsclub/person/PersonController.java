package at.fhv.sportsclub.person;

import at.fhv.sportsclub.common.CommonController;
import at.fhv.sportsclub.entity.person.PersonEntity;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.repository.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonController extends CommonController<PersonDTO, PersonEntity, PersonRepository> {

    private PersonRepository personRepository;

    @Autowired
    public PersonController(PersonRepository repository) {
        super(repository);
        this.personRepository = repository;
    }

    @Override
    protected PersonEntity internalMap(PersonDTO personDTO) {
        return this.map(personDTO, PersonEntity.class);
    }

    @Override
    protected String getId(PersonEntity entity) {
        return entity.getId();
    }

    @Override
    public List<PersonDTO> getAll() {
        List<PersonEntity> personEntities = this.personRepository.findAll();
        return mapCollection(personEntities, PersonDTO.class);
    }
}
