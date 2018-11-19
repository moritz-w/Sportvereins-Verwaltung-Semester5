package at.fhv.sportsclub.controller.impl;

import at.fhv.sportsclub.controller.common.CommonController;
import at.fhv.sportsclub.controller.common.RequiredPrivileges;
import at.fhv.sportsclub.controller.interfaces.IPersonController;
import at.fhv.sportsclub.entity.person.PersonEntity;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.security.AccessLevel;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.repository.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
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
    @RequiredPrivileges(category = "Person", accessLevel = {AccessLevel.READ})
    public ListWrapper<PersonDTO> getAllEntries(SessionDTO session) {
        return new ListWrapper<>(new ArrayList<>(this.getAll()), null);
    }

    @Override
    @RequiredPrivileges(category = "Person", accessLevel = {AccessLevel.WRITE})
    public ResponseMessageDTO saveOrUpdateEntry(SessionDTO session, PersonDTO personDTO) {
        return this.saveOrUpdate(personDTO);
    }

    @Override
    @RequiredPrivileges(category = "Person", accessLevel = {AccessLevel.READ})
    public PersonDTO getEntryDetails(SessionDTO session, String id) throws RemoteException {
        return this.getDetails(id);
    }

    //endregion
}
