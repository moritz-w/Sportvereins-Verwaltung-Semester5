package at.fhv.sportsclub.security.authentication;

import at.fhv.sportsclub.model.security.UserAuthentication;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

/*
      Created: 14.11.2018
      Author: Stefan Geiger.
      Co-Authors: Moritz W.
*/
@Component
public class LdapAuthenticationProvider implements AuthenticationProvider{

    @Override
    public boolean authenticate(UserAuthentication authentication) {
        String username = authentication.getId();
        String password = authentication.getCredentials();
        String url = "ldaps://dc01.ad.uclv.net:636";
        String base = "ou=fhusers,dc=ad,dc=uclv,dc=net";

        Hashtable<String, Object> ldapParams = new Hashtable<>();
        ldapParams.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        ldapParams.put(Context.PROVIDER_URL, url);
        ldapParams.put(Context.SECURITY_AUTHENTICATION, "simple");
        ldapParams.put(Context.SECURITY_PRINCIPAL, username);
        ldapParams.put(Context.SECURITY_CREDENTIALS, password);

        // Specify SSL
        ldapParams.put(Context.SECURITY_PROTOCOL, "ssl");
        ldapParams.put(Context.SECURITY_PROTOCOL, "ssl");

        InitialDirContext ldapCtx = null;
        try {
            ldapCtx = new InitialDirContext(ldapParams);
            if (ldapCtx != null) {
                return true;
            }
        } catch (AuthenticationException e) {
            return false;
        } catch (NamingException e){
            return false;
        }
        return false;
    }
}
