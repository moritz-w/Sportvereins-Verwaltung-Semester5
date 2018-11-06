package server;

import factory.IControllerFactory;
import factory.IControllerFactoryImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Alex on 06.11.2018.
 */

public class RMIServerInstance {
    private Registry registry;

    public RMIServerInstance() throws RemoteException {
        registry = LocateRegistry.createRegistry(1099);
        IControllerFactory stub = (IControllerFactory) UnicastRemoteObject.exportObject(new IControllerFactoryImpl(),0);
        registry.rebind("ControllerFactory", stub);
    }

    public Registry getRegistry() {
        return registry;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }
}

