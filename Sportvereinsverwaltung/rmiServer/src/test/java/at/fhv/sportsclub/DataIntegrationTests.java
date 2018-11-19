package at.fhv.sportsclub;

import at.fhv.sportsclub.starter.ServerRunMe;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;

/*
      Created: 13.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
public class DataIntegrationTests {

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
    public void insertAndFindNewPerson(){

    }
}
