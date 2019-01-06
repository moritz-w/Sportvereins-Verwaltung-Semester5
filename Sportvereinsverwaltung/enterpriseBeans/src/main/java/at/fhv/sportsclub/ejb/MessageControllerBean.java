package at.fhv.sportsclub.ejb;

import at.fhv.sportsclub.controller.interfaces.IMessageController;
import at.fhv.sportsclub.model.message.MessageDTO;
import at.fhv.sportsclub.model.security.SessionDTO;

import javax.ejb.Stateless;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/*
      Created: 19.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Stateless
public class MessageControllerBean implements at.fhv.sportsclub.ejb.interfaces.IMessageController {

    private final IMessageController messageController;

    public MessageControllerBean() {
        SpringContextBean contextBean = SpringContextBeanFactory.getInstance();
        this.messageController = contextBean.getMessageController();
    }

    @Override
    public void sendMessagesToQueue(SessionDTO sessionDTO, Map<String, String> messages) {
        try {
            messageController.sendMessagesToQueue(sessionDTO, messages);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageToQueue(SessionDTO sessionDTO, String message, String username) {
        try {
            messageController.sendMessageToQueue(sessionDTO, message, username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessageToQueue(SessionDTO sessionDTO, String message, String username, String replyTo) {
        try {
            messageController.sendMessageToQueue(sessionDTO, message, username, replyTo);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MessageDTO> browseMessagesForUser(SessionDTO sessionDTO, String username) {
        try {
            return messageController.browseMessagesForUser(sessionDTO, username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean removeMessageFromQueueAndArchive(SessionDTO sessionDTO, String correlationID, Boolean confirm) {
        try {
            return messageController.removeMessageFromQueueAndArchive(sessionDTO, correlationID, confirm);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }
}
