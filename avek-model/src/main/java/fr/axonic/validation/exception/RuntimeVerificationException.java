package fr.axonic.validation.exception;

/**
 * Created by cduffau on 12/08/16.
 */
public class RuntimeVerificationException extends RuntimeException{

    public RuntimeVerificationException(String message) {
        super(message);
    }

    public RuntimeVerificationException(Throwable cause) {
        super(cause);
    }
}
