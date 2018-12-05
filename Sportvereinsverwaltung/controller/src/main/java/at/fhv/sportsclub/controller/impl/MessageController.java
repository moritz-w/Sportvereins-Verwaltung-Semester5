package at.fhv.sportsclub.controller.impl;

import at.fhv.sportsclub.controller.interfaces.IMessageController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.*;
import javax.jms.Queue;

import at.fhv.sportsclub.model.message.MessageDTO;
import at.fhv.sportsclub.model.security.SessionDTO;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Alex on 25.11.2018.
 */
@Service
@Scope("prototype")
public class MessageController implements IMessageController {

    private ActiveMQConnectionFactory connectionFactory;

    private Connection connection;

    public MessageController() {
        this.connectionFactory = new ActiveMQConnectionFactory("tcp://10.0.51.91:61616/");
    }

    @Override
    public void sendMessagesToQueue(SessionDTO sessionDTO, Map<String, String> messages) {
        messages.forEach((s, s2) -> sendMessageToQueue(sessionDTO, s, s2));
    }

    @Override
    public void sendMessageToQueue(SessionDTO sessionDTO, String message, String username) {
        sendMessageToQueue(sessionDTO, message, username, null);
    }

    @Override
    public void sendMessageToQueue(SessionDTO sessionDTO, String message, String username, String replyTo) {
        try {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = session.createQueue("mainQueue");

            MessageProducer producer = session.createProducer(queue);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            TextMessage textMessage = session.createTextMessage(message);
            textMessage.setStringProperty("username", username);
            textMessage.setJMSCorrelationID(createRandomString());
            if(replyTo != null) {
                textMessage.setStringProperty("replyTo", replyTo);
            }

            producer.send(textMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MessageDTO> browseMessagesForUser(SessionDTO sessionDTO, String username) {
        LinkedList<MessageDTO> result = new LinkedList<>();

        try {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            QueueBrowser browser = session.createBrowser(session.createQueue("mainQueue"), "username='" + username + "'");
            Enumeration enumeration = browser.getEnumeration();

            // The newer Message will be insert at the beginning.
            while(enumeration.hasMoreElements()) {
                TextMessage next = (TextMessage) enumeration.nextElement();
                result.addFirst(
                        new MessageDTO(
                                next.getStringProperty("username"),
                                next.getText(),
                                next.getStringProperty("replyTo"),
                                next.getJMSCorrelationID(),
                                null
                        )
                );
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean removeMessageFromQueueAndArchive(SessionDTO sessionDTO, String correlationID, String replyMessage) {
        try {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue mainQueue = session.createQueue("mainQueue");
            MessageConsumer consumer = session.createConsumer(mainQueue, "JMSCorrelationID='" + correlationID + "'");
            //Wenn CorrelationID nicht existiert dann stockt es
            TextMessage receivedMessage = (TextMessage) consumer.receive();

            if(receivedMessage == null) {
                return false;
            }

            if(replyMessage != null) {
                String replyTo = receivedMessage.getStringProperty("replyTo");
                sendMessageToQueue(sessionDTO, receivedMessage.getText(), replyTo);
            }

            MessageProducer archiveQueueProducer = session.createProducer(session.createQueue("archiveQueue"));
            archiveQueueProducer.send(receivedMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return true;
    }

    @PostConstruct
    private void openConnection() {
         connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void closeConnection() {
        try  {
            if (connection != null) {
                connection.close();
            }
        } catch(JMSException jmse) {
            System.out.println("Could not close connection " + connection +" exception was " + jmse);
        }
    }

    private String createRandomString() {
        Random random = new Random(System.currentTimeMillis());
        long randomLong = random.nextLong();
        return Long.toHexString(randomLong);
    }
}
