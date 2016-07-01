package fr.axonic.avek.gui.model.verification.exception;

/**
 * This @Exception is used by the validation API. If an error is thrown
 * a @VerifiableError will be associated with the exception to explain where is
 * the problem.
 */
public abstract class VerificationException extends Exception {

    private VerifiableError errors;

    public VerificationException(VerifiableError errors) {
        this.errors = errors;
    }

    public VerifiableError getErrors() {
        return errors;
    }

}
