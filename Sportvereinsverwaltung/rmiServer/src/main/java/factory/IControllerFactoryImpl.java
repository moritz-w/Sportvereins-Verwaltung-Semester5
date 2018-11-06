package factory;

import remoteobjects.CreateMemberController;
import remoteobjects.controllerinterfaces.ICreateMemberController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Alex on 06.11.2018.
 */

public class IControllerFactoryImpl implements IControllerFactory {




    @Override
    public ICreateMemberController getCreateMemberController() throws RemoteException {
        ICreateMemberController controller = new CreateMemberController();
        ICreateMemberController stub = (ICreateMemberController) UnicastRemoteObject.exportObject(controller, 0);
        return stub;
    }
}
