package at.fhv.sportsclub.controller.interfaces;

import at.fhv.sportsclub.model.message.MessageDTO;

import java.rmi.Remote;

/**
 * Created by Alex on 25.11.2018.
 */

public interface IMessagingController extends Remote {

    void sendMessage(MessageDTO message);
    void receiveMessage();

}
