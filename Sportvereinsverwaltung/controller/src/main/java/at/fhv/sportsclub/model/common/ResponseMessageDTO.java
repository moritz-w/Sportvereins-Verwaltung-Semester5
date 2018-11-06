package at.fhv.sportsclub.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public @Data class ResponseMessageDTO {

    @NonNull private List<String> validationMessages;
    @NonNull private boolean validated;

    private boolean success = false;
    private String contextId = "";
    private String infoMessage = "";

    //public ResponseMessageDTO() {};

}
