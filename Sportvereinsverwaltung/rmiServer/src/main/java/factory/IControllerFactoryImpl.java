package factory;

import remoteobjects.CreateMemberController;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Alex on 06.11.2018.
 */

public class IControllerFactoryImpl implements IControllerFactory {


    @Override
    public Remote getCreateMemberController() throws RemoteException {
        return UnicastRemoteObject.exportObject(new CreateMemberController(),0);
    }
}
