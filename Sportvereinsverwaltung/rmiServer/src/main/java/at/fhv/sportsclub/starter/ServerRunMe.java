package at.fhv.sportsclub.starter;

import at.fhv.sportsclub.security.authentication.IAuthenticationController;
import at.fhv.sportsclub.factory.ControllerFactoryImpl;
import at.fhv.sportsclub.factory.IControllerFactory;
import at.fhv.sportsclub.security.authentication.AuthenticationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Alex on 06.11.2018.
 */

public class ServerRunMe extends Application {

    private static Logger rootLogger = Logger.getRootLogger();
    private static Registry registry;

    public static void createRMIRegistry(int port) throws RemoteException{
        ApplicationContext appContext = new ClassPathXmlApplicationContext("rmi-beans.xml");
        IControllerFactory controllerFactory = appContext.getBean(ControllerFactoryImpl.class);
        IAuthenticationController authenticationController = appContext.getBean(AuthenticationController.class);

        IControllerFactory stub = (IControllerFactory) UnicastRemoteObject.exportObject(controllerFactory,0);
        IAuthenticationController authStub =
                (IAuthenticationController) UnicastRemoteObject.exportObject(authenticationController, 0);

        registry = LocateRegistry.createRegistry(port <= 1 ? 1099 : port);
        // Auth Controller is exported as own object in the registry
        registry.rebind("AuthenticationService", authStub);
        registry.rebind("ControllerFactory", stub);

        rootLogger.info("RMI registry started");
    }

    public static void unbindRMIRegistry(){
        if(registry == null){return;}
        try {
            registry.unbind("AuthenticationService");
            registry.unbind("ControllerFactory");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Dashboard.fxml"));
        primaryStage.setTitle("Sportsclub Server");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) throws RemoteException {
        launch(args);
        //createRMIRegistry(1099);
    }
}
