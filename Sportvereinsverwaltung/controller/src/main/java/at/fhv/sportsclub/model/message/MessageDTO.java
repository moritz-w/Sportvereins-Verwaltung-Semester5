package at.fhv.sportsclub.model.message;

import at.fhv.sportsclub.model.common.IDTO;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
public @Data class MessageDTO implements Serializable, IDTO{
    public MessageDTO() {}

    private static final long serialVersionUID = 7759765143369106621L;

    private String userId;
    private List<String> receiver;

    private String message;
    private boolean responseable;

    private ResponseMessageDTO response;
}
