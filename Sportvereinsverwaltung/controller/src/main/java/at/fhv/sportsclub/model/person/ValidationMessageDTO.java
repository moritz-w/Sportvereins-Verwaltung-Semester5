package at.fhv.sportsclub.model.person;

import java.util.List;

public class ValidationMessageDTO {

    private String id;
    private List<String> validationMessages;
    private boolean validated;

    public ValidationMessageDTO() {};

    public ValidationMessageDTO(String id, List<String> validationMessages, boolean validated) {
        this.id = id;
        this.validationMessages = validationMessages;
        this.validated = validated;
    }

    //<editor-fold desc="Getter and Setter">
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getValidationMessages() {
        return validationMessages;
    }

    public void setValidationMessages(List<String> validationMessages) {
        this.validationMessages = validationMessages;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }
    //</editor-fold>
}
