package at.fhv.sportsclub.model.person;

import at.fhv.sportsclub.entity.person.AddressEntity;

import java.time.LocalDate;

public class PersonDTO {

    private String id, firstName, lastName;
    private LocalDate dateOfBirth;
    private AddressDTO address;
    private ContactDTO contact;
    private ValidationMessageDTO validationMessageDTO;

    public PersonDTO() {};

    public PersonDTO(String id, String firstName, String lastName, LocalDate dateOfBirth, AddressDTO address, ContactDTO contact, ValidationMessageDTO validationMessageDTO) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.contact = contact;
        this.validationMessageDTO = validationMessageDTO;
    }

    //<editor-fold desc="Getter and Setter">
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public ContactDTO getContact() {
        return contact;
    }

    public void setContact(ContactDTO contact) {
        this.contact = contact;
    }

    public ValidationMessageDTO getValidationMessageDTO() {
        return validationMessageDTO;
    }

    public void setValidationMessageDTO(ValidationMessageDTO validationMessageDTO) {
        this.validationMessageDTO = validationMessageDTO;
    }
    //</editor-fold>
}
