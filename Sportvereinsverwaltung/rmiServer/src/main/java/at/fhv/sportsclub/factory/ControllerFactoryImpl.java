package at.fhv.sportsclub.factory;

import at.fhv.sportsclub.controller.DepartmentController;
import at.fhv.sportsclub.controller.PersonController;
import at.fhv.sportsclub.controller.interfaces.IDepartmentController;
import at.fhv.sportsclub.controller.interfaces.IPersonController;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alex on 06.11.2018.
 */

@Component
public class ControllerFactoryImpl implements IControllerFactory {

    @Lookup
    public PersonController createPersonController() {
        return null;
    }
    @Lookup
    public DepartmentController createDepartmentController() {
        return null;
    }

    @Override
    public IPersonController getPersonController() throws RemoteException {
        return (IPersonController) UnicastRemoteObject.exportObject(createPersonController(), 0);
    }

    @Override
    public IDepartmentController getDepartmentController() throws RemoteException {
        return (IDepartmentController) UnicastRemoteObject.exportObject(createDepartmentController(), 0);
    }

}