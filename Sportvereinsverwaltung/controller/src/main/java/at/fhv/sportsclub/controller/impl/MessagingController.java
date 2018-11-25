package at.fhv.sportsclub.controller.impl;

import at.fhv.sportsclub.controller.interfaces.IMessagingController;
import at.fhv.sportsclub.model.message.MessageDTO;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * Created by Alex on 25.11.2018.
 */

public class MessagingController implements IMessagingController {

    @Resource(lookup = "java:/ConnectionFactory")
    ActiveMQConnectionFactory connectionFactory;

    @Resource(lookup = "java:jboss/activemq/queue/TestQueue")
    private Queue queue;

    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(messageBrokerUrl);

    @Override
    public void sendMessage(MessageDTO message) {
        Connection connection =  null;
        try {
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = session.createProducer(queue);

            connection.start();

            TextMessage jmsMessage = session.createTextMessage("Hello!");
            publisher.send(jmsMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        } finally
        {
            if(ic != null)
            {
                try  {
                    ic.close();
                }
                catch(Exception e) {
                    throw e;
                }
            }
            closeConnection(connection);
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
    public void receiveMessage() {

    }
}
