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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:controller-beans-test.xml"})
public class SimpleDozerMappingTest {

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    private LocalDate date = LocalDate.now();

    private AddressEntity address = new AddressEntity("1", "Hof", "6900", "Alberschwende");
    private ContactEntity contact = new ContactEntity("1", "06601709365", "test22@gmail.com");
    private PersonEntity person = new PersonEntity(null, "Lukas", "Stadel", date, address, contact, null);


    public SimpleDozerMappingTest() {
    }

    /*
        Test the Dozer mapping functionality with nested objects.
     */
    @Test
    public void testSimpleRecursiveDtoMapping() {
        LocalDate date = LocalDate.now();

        AddressEntity address = new AddressEntity("1", "Hof", "6900", "Alberschwende");
        ContactEntity contact = new ContactEntity("1", "06601709365", "test22@gmail.com");
        PersonEntity person = new PersonEntity("1", "Lukas", "Stadel", date, address, contact, null);

        PersonDTO mappedPerson = this.dozerBeanMapper.map(person, PersonDTO.class);

        assertEquals(mappedPerson.getFirstName(), person.getFirstName());
        assertEquals(mappedPerson.getAddress().getId(), person.getAddress().getId());
    }

    @Test
    public void testDifferentMappingXMLFiles() {

        /*
            Light Mapping - To check if only the 2 selected attributes
            (firstName and lastName) are mapped. All other attributes
            are not mapped --> null.
         */
        PersonDTO mappedPerson = mappingToDTOWithSpecifiedMappingId("PersonDTOMappingLight");
        assertEquals(mappedPerson.getFirstName(), person.getFirstName());
        assertNull(mappedPerson.getAddress());

        /*
            Full Mapping - This checks whether all attributes are mapped.
            The mapping file "PersonDTOMappingFull.xml" is used. No specific
            attributes are specified, but by default everything is mapped
            if the attribute names match.
         */
        PersonDTO mappedPersonFull = mappingToDTOWithSpecifiedMappingId("PersonDTOMappingFull");
        assertEquals(mappedPersonFull.getFirstName(), person.getFirstName());
        assertEquals(mappedPersonFull.getAddress().getId(),  person.getAddress().getId());
        assertNull(mappedPerson.getId());
    }

    public void setDozerBeanMapper(DozerBeanMapper dozerBeanMapper) {
        this.dozerBeanMapper = dozerBeanMapper;
    }

    /*
        A universal mapping method. Only the mapping ID must be passed.
        Mapping ID describes the MappingFile that is to be applied to the mapping.
     */
    public PersonDTO mappingToDTOWithSpecifiedMappingId(String mappingId) {
        return this.dozerBeanMapper.map(person, PersonDTO.class, mappingId);
    }
}