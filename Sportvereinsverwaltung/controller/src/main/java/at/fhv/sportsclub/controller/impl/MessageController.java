package at.fhv.sportsclub.controller.impl;

import at.fhv.sportsclub.controller.interfaces.IMessageController;
import at.fhv.sportsclub.model.message.MessageDTO;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by Alex on 25.11.2018.
 */
@Service
@Scope("prototype")
public class MessageController implements IMessageController {

    @Resource(lookup = "tcp://10.0.51.91:61616/")
    ActiveMQConnectionFactory connectionFactory;

    /*@Resource(lookup = "java:jboss/activemq/queue/TestQueue")
    private Queue queue;*/


    final String archiveQueue = "archiveQueue";
    final String mainQueue = "mainQueue";

    private Connection connection;



    static {
        clientQueueName = "mainQueue";
        ackMode = Session.AUTO_ACKNOWLEDGE;
    }

    @Override
    public void sendMessageToQueue(String message, String username) {
        try {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = session.createProducer(queue);

            connection.start();

            TextMessage jmsMessage = session.createTextMessage("Hello!");
            publisher.send(jmsMessage);*/
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    private void openConnection() {
         connection = null;
        try {
            connection = connectionFactory.createConnection();
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
}
