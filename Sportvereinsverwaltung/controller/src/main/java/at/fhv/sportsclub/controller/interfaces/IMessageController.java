package at.fhv.sportsclub.controller.interfaces;

import at.fhv.sportsclub.model.security.SessionDTO;

import javax.jms.Message;
import java.rmi.Remote;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 25.11.2018.
 */

public interface IMessageController extends Remote {

    /**
     * Send multiple messages to the queue.
     * @param messages Map<String(Username), String(messageText)>
     */
    void sendMessagesToQueue(Map<String, String> messages);
    void sendMessageToQueue(SessionDTO session, String message, String username);
    void sendMessageToQueue(SessionDTO session, String message, String username, String replyTo);
    List<Message> browseMessagesForUser(SessionDTO session, String username);
    boolean removeMessageFromQueueAndArchive(SessionDTO session, String correlationID, String replyToReceiver);

}
