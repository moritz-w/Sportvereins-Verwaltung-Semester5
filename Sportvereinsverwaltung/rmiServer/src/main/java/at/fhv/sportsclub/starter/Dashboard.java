package at.fhv.sportsclub.starter;

import at.fhv.sportsclub.controller.impl.ConfigurationController;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.apache.log4j.Logger;

import java.net.*;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/*
      Created: 04.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
public class Dashboard implements Initializable, UiNotify<String> {

    private static Logger logger = Logger.getRootLogger();

    public TextField portInput;
    public Button startStopBtn;
    public Label ipInfoLbl;
    public Label serverStatusLbl;
    public TextArea logOutputTxt;
    public Button resetBtn;
    public TextField scriptInput;

    private boolean started = false;
    private Thread rmiThread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            String ip = socket.getLocalAddress().getHostAddress();
            ipInfoLbl.setText(ip);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        resetBtn.setOnAction(event -> reloadDatabase());
        startStopBtn.setOnAction(event -> startStopServer());
        logger.addAppender(new UiAppender(this));
        logOutputTxt.textProperty().addListener((observable, oldValue, newValue) -> logOutputTxt.setScrollTop(Double.MAX_VALUE));
    }

    private void showErrorAlert(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error occured");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void update(String data){
        logOutputTxt.appendText(data +"\n");
    }


    private void startStopServer() {
        if (started){
            ServerRunMe.unbindRMIRegistry();
            setServerInactiveLabel();
            System.exit(0);
        } else {
            rmiThread = new Thread(() -> {
                try {
                    ServerRunMe.createRMIRegistry(new Integer(portInput.getText()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                    showErrorAlert(e.getMessage());
                }
            });
            rmiThread.setDaemon(true);
            rmiThread.start();
            setServerActiveLabel();
        }
    }

    private void setServerActiveLabel(){
        startStopBtn.setText("Stop");
        serverStatusLbl.setText("Server running");
        serverStatusLbl.setTextFill(Color.web("#42CF76"));
        started = true;
    }

    private void setServerInactiveLabel(){
        startStopBtn.setText("Launch");
        serverStatusLbl.setText("Server inactive");
        serverStatusLbl.setTextFill(Color.web("#E01F40"));
        started = false;
    }

    private void reloadDatabase(){
        if(scriptInput.getText().isEmpty()){
            return;
        }
        ConfigurationController configurationController = ServerRunMe.getConfigurationController();
        if (configurationController == null){
            showErrorAlert("Please launch the server first");
            return;
        }
        boolean success = configurationController.reloadDatabase(scriptInput.getText());
        if (success){
            logger.info("Database was reloaded!");
        } else {
            logger.warn("Invalid database script given: " + scriptInput.getText());
            showErrorAlert("Could not load given database script");
        }
    }

}
