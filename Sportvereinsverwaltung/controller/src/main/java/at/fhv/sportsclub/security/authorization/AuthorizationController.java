package at.fhv.sportsclub.security.authorization;

import at.fhv.sportsclub.controller.common.RequiredPrivileges;
import at.fhv.sportsclub.model.common.IDTO;
import at.fhv.sportsclub.model.common.ListWrapper;
import at.fhv.sportsclub.model.common.ResponseMessageDTO;
import at.fhv.sportsclub.model.security.AccessLevel;
import at.fhv.sportsclub.model.security.PrivilegeDTO;
import at.fhv.sportsclub.model.security.RoleDTO;
import at.fhv.sportsclub.model.security.SessionDTO;
import at.fhv.sportsclub.security.authentication.AuthenticationController;
import at.fhv.sportsclub.security.exception.SessionInvalidException;
import at.fhv.sportsclub.security.session.SessionManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

/*
      Created: 13.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Component
public class AuthorizationController {

    private static final Logger logger = Logger.getRootLogger();
    private final SessionManager sessionManager;

    @Autowired
    public AuthorizationController(SessionManager sessionManager){
        this.sessionManager = sessionManager;
    }

    public Object checkPermissions(ProceedingJoinPoint joinPoint, SessionDTO session){
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Class returnType = signature.getReturnType();
            if (session == null) {
                return injectResponse(returnType, "Invalid session");
            }
            Method calledMethod = signature.getMethod();
            RequiredPrivileges requiredPrivileges = calledMethod.getAnnotation(RequiredPrivileges.class);
            if (!requiredPrivileges.authenticationRequired()) {
                return joinPoint.proceed();
            }
            try {
                List<RoleDTO> accessRoles = this.sessionManager.getAccessRolesBySession(session);
                List<PrivilegeDTO> privileges = new LinkedList<>();
                for (RoleDTO accessRole : accessRoles) {
                    privileges.addAll(accessRole.getPrivileges());
                }
                if (isAuthorized(privileges, requiredPrivileges.category(), requiredPrivileges.accessLevel())) {
                    return joinPoint.proceed();
                } else {
                    return injectResponse(returnType, "Insufficient access rights");
                }
            } catch (SessionInvalidException e) {
                return injectResponse(returnType, e.getMessage());

            }
        } catch (Throwable throwable) {
            // in case the wrapped method throws an exception
            throwable.printStackTrace();
        }
        return null;
    }

    /**
     * Compares the required with the given access rights with the help of HashSets
     * @param privileges the privileges the user has (loaded from the session store)
     * @param domain the requested domain access
     * @param required the access level needed to access the domain
     * @return True if the required access level matches the given privileges
     */
    private boolean isAuthorized(List<PrivilegeDTO> privileges, String domain, AccessLevel[] required){
        for (PrivilegeDTO privilege : privileges) {
            if(privilege.getDomain().equals(domain)){
                return privilege.getAccessLevels().containsAll(new HashSet<>(Arrays.asList(required)));
            }
        }
        return false;
    }

    private Object injectResponse(Class returnType, String message){
        ResponseMessageDTO response = new ResponseMessageDTO(new LinkedList<>(), false);
        response.setSuccess(false);
        response.setInfoMessage(message);
        try {
            Object instance = returnType.newInstance();
            if (instance instanceof ListWrapper) {
                return new ListWrapper<>(null, response);
            } else if (instance instanceof ResponseMessageDTO){
                return response;
            } else if (instance instanceof IDTO){
                ((IDTO) instance).setResponse(response);
                return instance;
            } else {
                throw new IllegalArgumentException("Controller return type must be of type IDTO, ListWrapper or ResponseMessageDTO");
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
