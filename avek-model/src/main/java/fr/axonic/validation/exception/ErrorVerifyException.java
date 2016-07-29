package fr.axonic.validation.exception;

/**
 * Created by lbrouchet on 07/07/2015.
 * This Exception is thrown when an error occurred during the validation.
 */
public class ErrorVerifyException extends VerificationException {

    public ErrorVerifyException(VerifiableError errors){
        super(errors);
    }

}
