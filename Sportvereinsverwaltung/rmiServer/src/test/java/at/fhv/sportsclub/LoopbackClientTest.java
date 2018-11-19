package at.fhv.sportsclub;

import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.person.AddressDTO;
import at.fhv.sportsclub.model.person.ContactDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.model.security.SessionDTO;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.junit.BeforeClass;
import org.junit.Test;

import at.fhv.sportsclub.starter.ServerRunMe;
import at.fhv.sportsclub.controller.interfaces.*;
import at.fhv.sportsclub.factory.IControllerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
      Created: 13.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
public class LoopbackClientTest {

    private static int port = 1099;

    @BeforeClass
    public static void startServer(){
        try {
            ServerRunMe.createRMIRegistry(port);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRMIConnectionAndLookupFactory() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(port);
        IControllerFactory controllerFactory = (IControllerFactory) registry.lookup("ControllerFactory");
        IPersonController personController = controllerFactory.getPersonController();
        //ArrayList<PersonDTO> allEntries = personController.getAllEntries(null);
        //IAuthenticationController authenticationController = controllerFactory.getAuthenticationController();
        //String pw = "test";
        //SessionDTO session = authenticationController.authenticate("mwi4339@students.fhv.at", pw.toCharArray());

        IDepartmentController departmentController = controllerFactory.getDepartmentController();
        SportDTO randomSport = departmentController.getAllSportEntries().get(0);
        AddressDTO address = new AddressDTO(null, "Memory lane", "000", "Compton", null);
        ContactDTO contact = new ContactDTO(null, "0009", "mw@gmail.com", null);
        ArrayList<SportDTO> sports = new ArrayList<>();
        sports.add(randomSport);
        ResponseMessageDTO responseMessageDTO = personController.saveOrUpdateEntry(
                new PersonDTO(null, "Alfons", "Hatler", LocalDate.now(), address, contact, sports, null)
        );
        PersonDTO entryDetails = personController.getEntryDetails(responseMessageDTO.getContextId());

        //ArrayList<PersonDTO> allEntries = personController.getAllEntries(null);
        //PersonDTO snoopDogg = personController.getEntryDetails(allEntries.get(0).getId());
       // System.out.println(snoopDogg.toString());
    }


}
