import at.fhv.sportsclub.factory.ControllerFactoryImpl;
import at.fhv.sportsclub.factory.IControllerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Alex on 06.11.2018.
 */

public class ServerRunMe {

    public static void main(String[] args) throws RemoteException {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("rmi-beans.xml");
        IControllerFactory controllerFactory = appContext.getBean(ControllerFactoryImpl.class);

        IControllerFactory stub = (IControllerFactory) UnicastRemoteObject.exportObject(controllerFactory,0);

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("ControllerFactory", stub);

        System.out.println("RMI server is running!");
    }
}
