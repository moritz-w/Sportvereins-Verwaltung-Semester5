package at.fhv.sportsclub.starter;

import at.fhv.sportsclub.controller.impl.ConfigurationController;
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
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Alex on 06.11.2018.
 */

public class ServerRunMe extends Application {

    private static Logger rootLogger = Logger.getRootLogger();
    private static ConfigurationController configurationController;
    private static Registry registry;

    public static void createRMIRegistry(int port) throws RemoteException{
        ApplicationContext appContext = new ClassPathXmlApplicationContext(     // "persist-beans.xml", "controller-beans.xml", "security-beans.xml",
                "rmi-beans.xml"
        );
        boolean active = ((ClassPathXmlApplicationContext) appContext).isActive();
        IControllerFactory controllerFactory = appContext.getBean(ControllerFactoryImpl.class);
        configurationController = appContext.getBean(ConfigurationController.class);

        IAuthenticationController authenticationController = appContext.getBean(AuthenticationController.class);

        IControllerFactory stub = (IControllerFactory) UnicastRemoteObject.exportObject(controllerFactory,0);
        IAuthenticationController authStub =
                (IAuthenticationController) UnicastRemoteObject.exportObject(authenticationController, 0);

        registry = LocateRegistry.createRegistry(port <= 1 ? 1090 : port);
        // Auth Controller is exported as own object in the registry
        registry.rebind("AuthenticationService", authStub);
        registry.rebind("ControllerFactory", stub);

        rootLogger.info("RMI registry started");
    }

    static void unbindRMIRegistry(){
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
        Options options = argParse(args);
        if(options.isSet("u")){
            int port = 1090;
            if(options.isSet("p")){
                port = new Integer(options.getParam("p"));
            }
            createRMIRegistry(port);
        } else {
            launch(args);
        }
    }

    private static Options argParse(String[] args){
        Options options = new Options();
        for (String arg : args) {
            options.addStringArg(arg);
        }
        return options;
    }

    private static class Options {
        private HashMap<String, String> argMap;
        private String previous;

        Options() {
            this.argMap = new HashMap<>();
        }

        void addStringArg(String arg){
            if (arg.contains("-")) {
                previous = arg.replace("-", "");
                argMap.put(previous, "");
            } else {
                argMap.put(previous, arg);
            }
        }

        boolean isSet(String arg){
            return argMap.containsKey(arg);
        }

        String getParam(String arg){
            return argMap.getOrDefault(arg, "");
        }

    }

    static ConfigurationController getConfigurationController(){
        return configurationController;
    }
}
