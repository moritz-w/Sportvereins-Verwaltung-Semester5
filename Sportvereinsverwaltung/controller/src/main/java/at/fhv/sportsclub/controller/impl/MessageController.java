package at.fhv.sportsclub.controller.impl;

import at.fhv.sportsclub.controller.interfaces.IMessageController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.jms.*;
import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * Created by Alex on 25.11.2018.
 */
@Component
@Scope("prototype")
public class MessageController implements IMessageController {

    @Resource(lookup = "tcp://10.0.51.91:61616/")
    ActiveMQConnectionFactory connectionFactory;

    /*@Resource(lookup = "java:jboss/activemq/queue/TestQueue")
    private Queue queue;*/


    /*enum Queuenames {
        MAIN_QUEUE (""),
        ARCHIVE_QUEUE ("archiveQueue");
        @Getter private String name;
    }*/

    private Connection connection;

    @Override
    public void sendMessagesToQueue(Map<String, String> messages) {
        messages.forEach(new BiConsumer<String, String>() {
            @Override
            public void accept(String s, String s2) {
                sendMessageToQueue(s, s2);
            }
        });
    }

    @Override
    public void sendMessageToQueue(String message, String username) {
        sendMessageToQueue(message, username, null);
    }

    @Override
    public void sendMessageToQueue(String message, String username, String replyTo) {
        try {
            openConnection();
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
            closeConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Message> browseMessagesForUser(String username) {
        LinkedList<Message> result = new LinkedList<>();

        try {
            openConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            QueueBrowser browser = session.createBrowser(session.createQueue("mainQueue"), "username = " + username);
            Enumeration enumeration = browser.getEnumeration();

            // The newer Message will be insert at the beginning.
            while(enumeration.hasMoreElements()) {
                result.addFirst((Message) enumeration.nextElement());
            }

            closeConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean removeMessageFromQueueAndArchive(String correlationID, String replyMessage) {
        try {
            openConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue mainQueue = session.createQueue("mainQueue");
            MessageConsumer consumer = session.createConsumer(mainQueue, "JMSCorrelationID = " + correlationID);
            Message receivedMessage = consumer.receive();

            if(receivedMessage == null) {
                return false;
            }

            if(replyToReceiver != null) {
                receivedMessage.

                MessageProducer producer = session.createProducer(mainQueue);
                producer.send(receivedMessage);
            }

            MessageProducer archiveQueueProducer = session.createProducer(session.createQueue("archiveQueue"));
            archiveQueueProducer.send(receivedMessage);

            closeConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return true;
    }

    @PostConstruct // ?
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
