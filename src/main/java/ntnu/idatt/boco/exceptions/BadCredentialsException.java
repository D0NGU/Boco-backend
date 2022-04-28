package ntnu.idatt.boco.exceptions;

public class BadCredentialsException extends Exception {
    public BadCredentialsException(String errorMsg) {
        super(errorMsg);
    }
}
