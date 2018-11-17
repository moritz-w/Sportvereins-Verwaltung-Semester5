package at.fhv.sportsclub.security.session;

import at.fhv.sportsclub.model.security.UserDetails;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
      Created: 14.11.2018
      Author: Moritz W.
      Co-Authors: 
*/
@Component
public class SimpleSessionIdService implements SessionIdService<String> {

    private final static Logger logger = Logger.getRootLogger();
    private final static String secret = "9GOy5Bs7vXmmT4SFaaq9Y1WMM1fRy59Q";

    @Override
    public String generateSessionId(UserDetails userDetails) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            String val = userDetails.getUserId() + userDetails.getNonce() + secret;
            byte[] hashBytes = messageDigest.digest(val.getBytes(StandardCharsets.UTF_8));
            return new String(hashBytes, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Could not generate session ", e);
            throw new RuntimeException("Fatal error: could not obtain encoder for given digest type");
        }
    }

    @Override
    public boolean validateSessionId(String sessionId, UserDetails userDetails) {
        return generateSessionId(userDetails).equals(sessionId);
    }
}
