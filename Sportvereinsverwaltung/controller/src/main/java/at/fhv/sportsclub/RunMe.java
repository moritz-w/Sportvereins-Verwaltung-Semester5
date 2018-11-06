package at.fhv.sportsclub;

import at.fhv.sportsclub.controller.DepartmentController;
import at.fhv.sportsclub.controller.PersonController;
import at.fhv.sportsclub.entity.person.AddressEntity;
import at.fhv.sportsclub.entity.person.ContactEntity;
import at.fhv.sportsclub.entity.person.PersonEntity;
import at.fhv.sportsclub.model.dept.SportDTO;
import at.fhv.sportsclub.model.person.PersonDTO;
import org.dozer.DozerBeanMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;

public class RunMe {

    public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("persist-beans.xml");
        DepartmentController controller = appContext.getBean(DepartmentController.class);

        for (SportDTO sportDTO : controller.getAllSports()) {
            System.out.println(sportDTO.toString());
        }
    }
}
