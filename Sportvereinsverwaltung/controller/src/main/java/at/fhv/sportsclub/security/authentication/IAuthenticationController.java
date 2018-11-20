package at.fhv.sportsclub.security.authentication;

import at.fhv.sportsclub.model.security.SessionDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
      Created: 13.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
/**
 * The reason why this interface is in a separate package, is that Spring AOP
 * scans at.fhv.sportsclub.controller.interfaces package and inserts proxies into
 * those interface. But for the Authentication Controller, no proxy should intercept
 * the method calls. That's why it was moved to this package.
 */
public interface IAuthenticationController extends Remote{
    SessionDTO authenticate(String userId, char[] password) throws RemoteException;
}
