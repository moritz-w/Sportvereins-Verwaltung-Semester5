package remoteobjects;

import at.fhv.sportsclub.model.person.PersonDTO;
import remoteobjects.controllerinterfaces.ICreateMemberController;

/**
 * Created by Alex on 06.11.2018.
 */

public class CreateMemberController implements ICreateMemberController {

    public void createMember() {
        new PersonDTO();
    }

}
