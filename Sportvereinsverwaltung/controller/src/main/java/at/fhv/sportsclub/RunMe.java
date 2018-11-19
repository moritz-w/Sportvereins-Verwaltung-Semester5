package at.fhv.sportsclub;

import at.fhv.sportsclub.controller.impl.DepartmentController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RunMe {

    public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("controller-beans.xml");
        DepartmentController controller = appContext.getBean(DepartmentController.class);
    }
}
