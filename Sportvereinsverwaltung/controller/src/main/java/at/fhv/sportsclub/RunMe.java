package at.fhv.sportsclub;

import at.fhv.sportsclub.person.PersonController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RunMe {

    public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("persist-beans.xml");
        PersonController controller = appContext.getBean(PersonController.class);
    }
}
