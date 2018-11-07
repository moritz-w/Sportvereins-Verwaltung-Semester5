package at.fhv.sportsclub;

import at.fhv.sportsclub.controller.DepartmentController;
import at.fhv.sportsclub.model.dept.SportDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RunMe {

    public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("controller-beans.xml");
        DepartmentController controller = appContext.getBean(DepartmentController.class);

        for (SportDTO sportDTO : controller.getAllSports()) {
            System.out.println(sportDTO.toString());
        }
    }
}
