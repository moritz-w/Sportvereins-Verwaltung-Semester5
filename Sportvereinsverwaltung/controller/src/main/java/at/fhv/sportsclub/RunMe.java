package at.fhv.sportsclub;

import at.fhv.sportsclub.entity.person.AddressEntity;
import at.fhv.sportsclub.entity.person.ContactEntity;
import at.fhv.sportsclub.entity.person.PersonEntity;
import at.fhv.sportsclub.model.person.PersonDTO;
import at.fhv.sportsclub.person.PersonController;
import org.dozer.DozerBeanMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;

public class RunMe {

    public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("persist-beans.xml");
        PersonController controller = appContext.getBean(PersonController.class);

        DozerBeanMapper mapper = new DozerBeanMapper();

        LocalDate date = LocalDate.now();

        AddressEntity address = new AddressEntity("1","Hof", "6900", "Alberschwende");
        ContactEntity contact = new ContactEntity("1", "06601709365", "test22@gmail.com");
        PersonEntity person = new PersonEntity("1", "Lukas", "Stadel",date, address, contact);

        PersonDTO personMapped = mapper.map(person, PersonDTO.class);

        System.out.println(personMapped.getFirstName().equals("Lukas"));
    }
}
