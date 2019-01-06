package at.fhv.sportsclub;

import at.fhv.sportsclub.controller.impl.DepartmentController;
import at.fhv.sportsclub.controller.impl.MessageController;
import at.fhv.sportsclub.controller.impl.TeamController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RunMe {

    public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("controller-beans.xml");
        DepartmentController controller = appContext.getBean(DepartmentController.class);
        TeamController teamController  = appContext.getBean(TeamController.class);

        MessageController messageController = appContext.getBean(MessageController.class);
        messageController.sendMessageToQueue(null, "Normal message", "5c06ec68b4b0bb88621d5201");
        messageController.sendMessageToQueue(null, "Normal message", "5c06ec68b4b0bb88621d5201", "5c06ec68b4b0bb88621d5201");
    }
}
