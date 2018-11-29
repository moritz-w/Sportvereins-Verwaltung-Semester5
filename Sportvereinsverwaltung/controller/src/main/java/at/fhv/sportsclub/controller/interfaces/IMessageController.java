package at.fhv.sportsclub.controller.interfaces;

import at.fhv.sportsclub.model.message.MessageDTO;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.rmi.Remote;
import java.util.List;

/**
 * Created by Alex on 25.11.2018.
 */

public interface IMessageController extends Remote, MessageListener {

    void sendMessageToQueue(String message, String username);
    void sendMessagesToQueue(Map<User, Message>);// sendMessagesToQueue(List<Message>, List<User>)
    List<Message> browseMessagesForUser(String User) (UserString für messageSelektor); //With Browser
    deleteMessageFromQueue(CorrelationID);
    sendMessageToArchiveQueue(Message);
    getAllMessagesFromQueue();
    browseAllMessagesFromQueue();

}
