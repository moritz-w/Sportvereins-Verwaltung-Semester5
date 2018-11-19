package at.fhv.sportsclub.controller.interfaces;

import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.model.person.PersonDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IPersonController extends Remote {

    ListWrapper<PersonDTO> getAllEntries(SessionDTO session) throws RemoteException;

    PersonDTO getEntryDetails(SessionDTO session, String id) throws RemoteException;

    ResponseMessageDTO saveOrUpdateEntry(SessionDTO session, PersonDTO personDTO) throws RemoteException;
}
