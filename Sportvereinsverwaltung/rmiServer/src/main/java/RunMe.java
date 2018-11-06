import factory.IControllerFactory;
import factory.IControllerFactoryImpl;
import remoteobjects.controllerinterfaces.ICreateMemberController;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Alex on 06.11.2018.
 */

public class RunMe {

    public static void main(String[] args) throws RemoteException {
        IControllerFactory server = new IControllerFactoryImpl();
        IControllerFactory stub = (IControllerFactory) UnicastRemoteObject.exportObject(server,0);

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("ControllerFactory", stub);

        System.out.println("RMI server is running!");
    }
}
