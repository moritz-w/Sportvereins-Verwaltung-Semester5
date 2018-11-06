package at.fhv.sportsclub.model.person;

public class ContactDTO {

    private String id, phoneNumber, emailAddress;

    public ContactDTO() {};

    public ContactDTO(String id, String phoneNumber, String emailAddress) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    //<editor-fold desc="Getter and Setter">
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    //</editor-fold>
}
