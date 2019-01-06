package at.fhv.sportsclub.ejb;

import at.fhv.sportsclub.ejb.interfaces.IPersonController;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.model.security.SessionDTO;

import javax.ejb.Stateless;
import java.rmi.RemoteException;

/*
      Created: 11.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Stateless
public class PersonControllerBean implements IPersonController {

    private at.fhv.sportsclub.controller.interfaces.IPersonController personController;

    public PersonControllerBean(){
        SpringContextBean contextBean = SpringContextBeanFactory.getInstance();
        this.personController = contextBean.getPersonController();
    }

    @Override
    public ListWrapper<PersonDTO> getAllEntries(SessionDTO session) {
        try {
            return this.personController.getAllEntries(session);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PersonDTO getEntryDetails(SessionDTO session, String id) {
        try {
            return this.personController.getEntryDetails(session, id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseMessageDTO saveOrUpdateEntry(SessionDTO session, PersonDTO personDTO) {
        try {
            return this.personController.saveOrUpdateEntry(session, personDTO);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
