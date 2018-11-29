package at.fhv.sportsclub.controller.interfaces;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import java.rmi.Remote;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 25.11.2018.
 */

public interface IMessageController extends Remote, MessageListener {

    void sendMessageToQueue(String message, String username);

    /**
     * Send multiple messages to the queue.
     * @param messages Map<String(Username), String(messageText)>
     */
    void sendMessagesToQueue(Map<String, String> messages);// sendMessagesToQueue(List<Message>, List<User>)

    List<Message> browseMessagesForUser(String username); // (UserString für messageSelektor); //With Browser
    boolean deleteMessageFromQueue(String correlationID);
    boolean deleteMessagesFromQueue(List<String> correlationIDs);
    void sendMessageToArchiveQueue(Message message);
    Queue getAllMessagesFromQueue();
    Queue browseAllMessagesFromQueue();

}
