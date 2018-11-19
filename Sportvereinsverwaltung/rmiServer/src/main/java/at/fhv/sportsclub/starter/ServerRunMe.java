package at.fhv.sportsclub.starter;

import at.fhv.sportsclub.factory.ControllerFactoryImpl;
import at.fhv.sportsclub.factory.IControllerFactory;
import org.apache.log4j.Logger;
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

    private static Logger rootLogger = Logger.getRootLogger();

    public static void createRMIRegistry(int port) throws RemoteException{
        ApplicationContext appContext = new ClassPathXmlApplicationContext("rmi-beans.xml");
        IControllerFactory controllerFactory = appContext.getBean(ControllerFactoryImpl.class);

        IControllerFactory stub = (IControllerFactory) UnicastRemoteObject.exportObject(controllerFactory,0);

        Registry registry = LocateRegistry.createRegistry(port <= 1 ? 1099 : port);
        registry.rebind("ControllerFactory", stub);

        rootLogger.info("RMI registry started");
    }

    public static void main(String[] args) throws RemoteException {
        createRMIRegistry(1099);
    }
}
