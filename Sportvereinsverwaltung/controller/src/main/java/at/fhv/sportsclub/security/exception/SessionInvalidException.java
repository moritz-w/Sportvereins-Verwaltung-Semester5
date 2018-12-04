package at.fhv.sportsclub.security.exception;

public class SessionInvalidException extends Exception {
    public SessionInvalidException(String message) {
        super(message);
    }
}