package at.fhv.sportsclub.controller.interfaces;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.person.PersonDTO;

import java.rmi.Remote;
import java.util.List;

public interface IPersonController extends Remote {

    List<PersonDTO> getAllEntries();

    ResponseMessageDTO saveOrUpdateEntry(PersonDTO personDTO);
}
