package at.fhv.sportsclub.model.common;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@RequiredArgsConstructor
public @Data class ResponseMessageDTO implements Serializable {

    private static final long serialVersionUID = 6529685098267757690L;
    @NonNull private List<String> validationMessages;
    @NonNull private boolean validated;

    private boolean success = false;
    private String contextId = "";
    private String infoMessage = "";

    //public ResponseMessageDTO() {};

}
