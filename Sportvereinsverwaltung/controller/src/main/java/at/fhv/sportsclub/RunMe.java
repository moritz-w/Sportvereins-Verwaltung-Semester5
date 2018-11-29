package at.fhv.sportsclub;

import at.fhv.sportsclub.controller.impl.DepartmentController;
import at.fhv.sportsclub.controller.impl.MessageController;
import at.fhv.sportsclub.controller.impl.SimpleMessageListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.*;

public class RunMe {

    /*public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("controller-beans.xml");
        DepartmentController controller = appContext.getBean(DepartmentController.class);
    }*/

    public static void main(String[] args) {
        MessageController messageController = new MessageController();
        messageController.testConnection();
    }
}
