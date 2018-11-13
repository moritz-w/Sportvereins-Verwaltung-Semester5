package at.fhv.sportsclub;

import at.fhv.sportsclub.model.person.PersonDTO;
import org.junit.BeforeClass;
import org.junit.Test;

import at.fhv.sportsclub.starter.ServerRunMe;
import at.fhv.sportsclub.controller.interfaces.*;
import at.fhv.sportsclub.factory.IControllerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

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
        ArrayList<PersonDTO> allEntries = personController.getAllEntries();
        PersonDTO snoopDogg = personController.getEntryDetails(allEntries.get(0).getId());
        System.out.println(snoopDogg.toString());
    }

}
