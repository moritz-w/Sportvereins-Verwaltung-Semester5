package at.fhv.sportsclub.controller.impl;

import at.fhv.sportsclub.controller.interfaces.IMessageController;
import at.fhv.sportsclub.model.message.MessageDTO;

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
//@Service
//@Scope("prototype")
public class MessageController implements IMessageController, MessageListener {

    private String messageBrokerUrl = "tcp://10.0.51.91:8161/";

    //@Resource(lookup = "java:/ConnectionFactory")
    //ActiveMQConnectionFactory connectionFactory;

    /*@Resource(lookup = "java:jboss/activemq/queue/TestQueue")
    private Queue queue;*/

    ActiveMQConnection connectionFactory;

    @Override
    public void sendMessageToQueue(MessageDTO message) {
        //connectionFactory = new ActiveMQConnection.makeConnection(messageBrokerUrl);

        Connection connection =  null;
        try {
            //connection = connectionFactory.createConnection();
            connection.start();
            connection.close();

            /*Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = session.createProducer(queue);

            connection.start();

            TextMessage jmsMessage = session.createTextMessage("Hello!");
            publisher.send(jmsMessage);*/
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection(Connection con) {
        try  {
            if (con != null) {
                con.close();
            }
        } catch(JMSException jmse) {
            System.out.println("Could not close connection " + con +" exception was " + jmse);
        }
    }

    @Override
    public void receiveMessageFromQueue() {

    }

    public void testConnection() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://10.0.51.91:61616");
        try {
            Connection connection = activeMQConnectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination adminQueue = session.createQueue("messageQueue");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private static int ackMode;
    private static String clientQueueName;

    private boolean transacted = false;
    private MessageProducer producer;

    static {
        clientQueueName = "client.messages";
        ackMode = Session.AUTO_ACKNOWLEDGE;
    }

    public MessageController() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://10.0.51.91:61616");
        Connection connection;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(transacted, ackMode);
            Destination adminQueue = session.createQueue(clientQueueName);

            //Setup a message producer to send message to the queue the server is consuming from
            this.producer = session.createProducer(adminQueue);
            this.producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            //Create a temporary queue that this client will listen for responses on then create a consumer
            //that consumes message from this temporary queue...for a real application a client should reuse
            //the same temp queue for each message to the server...one temp queue per client
            Destination tempDest = session.createTemporaryQueue();
            MessageConsumer responseConsumer = session.createConsumer(tempDest);

            //This class will handle the messages to the temp queue as well
            responseConsumer.setMessageListener(null);

            //Now create the actual message you want to send
            TextMessage txtMessage = session.createTextMessage();
            txtMessage.setText("MyProtocolMessage");

            //Set the reply to field to the temp queue you created above, this is the queue the server
            //will respond to
            txtMessage.setJMSReplyTo(tempDest);

            //Set a correlation ID so when you get a response you know which sent message the response is for
            //If there is never more than one outstanding message to the server then the
            //same correlation ID can be used for all the messages...if there is more than one outstanding
            //message to the server you would presumably want to associate the correlation ID with this
            //message somehow...a Map works good
            String correlationId = this.createRandomString();
            txtMessage.setJMSCorrelationID(correlationId);
            this.producer.send(txtMessage);
        } catch (JMSException e) {
            //Handle the exception appropriately
        }
    }

}
