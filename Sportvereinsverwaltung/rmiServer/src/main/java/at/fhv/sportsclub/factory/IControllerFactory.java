package at.fhv.sportsclub.factory;

import at.fhv.sportsclub.controller.interfaces.IDepartmentController;
import at.fhv.sportsclub.controller.interfaces.IPersonController;
import at.fhv.sportsclub.controller.interfaces.ITeamController;
import at.fhv.sportsclub.controller.interfaces.ITournamentController;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * Created by Alex on 06.11.2018.
 */

public interface IControllerFactory extends Remote {
    IPersonController getPersonController() throws RemoteException;
    IDepartmentController getDepartmentController() throws RemoteException;
    ITeamController getTeamController() throws RemoteException;
    ITournamentController getTournamentController() throws RemoteException;
}
