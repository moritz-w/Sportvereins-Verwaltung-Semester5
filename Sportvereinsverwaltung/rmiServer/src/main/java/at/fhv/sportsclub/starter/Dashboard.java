package at.fhv.sportsclub.starter;

import com.mongodb.connection.Server;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.*;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/*
      Created: 04.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
public class Dashboard implements Initializable {

    public TextField portInput;
    public Button startStopBtn;
    public Label ipInfoLbl;
    public Label serverStatusLbl;

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

        startStopBtn.setOnAction(event -> {
            if (started){
                serverStatusLbl.setTextFill(Color.web("#E01F40"));
                serverStatusLbl.setText("Server inactive");
                startStopBtn.setText("Launch");
                ServerRunMe.unbindRMIRegistry();
                started = false;
                System.exit(0);
            } else {
                startStopBtn.setText("Stop");
                serverStatusLbl.setText("Server running");
                serverStatusLbl.setTextFill(Color.web("#42CF76"));
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
                started = true;
            }
        });
    }

    private void showErrorAlert(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error occured");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }


}
