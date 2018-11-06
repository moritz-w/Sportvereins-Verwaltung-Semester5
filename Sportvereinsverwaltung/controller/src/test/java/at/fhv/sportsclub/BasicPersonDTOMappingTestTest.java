package at.fhv.sportsclub;

import at.fhv.sportsclub.entity.person.AddressEntity;
import at.fhv.sportsclub.entity.person.ContactEntity;
import at.fhv.sportsclub.entity.person.PersonEntity;
import at.fhv.sportsclub.model.person.PersonDTO;
import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:controller-beans-test.xml"})
public class BasicPersonDTOMappingTestTest {

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    public BasicPersonDTOMappingTestTest() {}

    /*
        Test the Dozer mapping functionality with nested objects.
     */
    @Test
    public void testSimpleRecursiveDtoMapping() {
        LocalDate date = LocalDate.now();

        AddressEntity address = new AddressEntity("1","Hof", "6900", "Alberschwende");
        ContactEntity contact = new ContactEntity("1", "06601709365", "test22@gmail.com");
        PersonEntity person = new PersonEntity("1", "Lukas", "Stadel",date, address, contact);

        PersonDTO mappedPerson = this.dozerBeanMapper.map(person, PersonDTO.class);

        assertEquals(mappedPerson.getFirstName(), person.getFirstName());
        assertEquals(mappedPerson.getAddress().getId(), person.getAddress().getId());
    }

    public void setDozerBeanMapper(DozerBeanMapper dozerBeanMapper) {
        this.dozerBeanMapper = dozerBeanMapper;
    }
}