package at.fhv.sportsclub;

import at.fhv.sportsclub.model.person.PersonDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:controller-beans-test.xml"})
public class SimpleValidationTestTest {

    @Autowired
    private Validator validator;

    private PersonDTO invalidNameDTO =
            new PersonDTO(null, "Invalid1", "Invalid2", LocalDate.now(), null, null, null, null);

    public SimpleValidationTestTest() {
    }

    @Test
    public void testRegexValidation() {
        List<String> violationMessages = new LinkedList<>();
        Set<ConstraintViolation<PersonDTO>> constraintViolations = this.validator.validate(this.invalidNameDTO);
        assertFalse(constraintViolations.isEmpty());
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}