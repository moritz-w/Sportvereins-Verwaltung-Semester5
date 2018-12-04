package at.fhv.sportsclub.starter;

import com.mongodb.connection.Server;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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


}
