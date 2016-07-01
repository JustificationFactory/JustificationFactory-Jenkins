package fr.axonic.avek.model.verification;


import fr.axonic.avek.model.verification.exception.VerificationException;

/**
 * Created by lbrouchet on 07/07/2015.
 *
 * This interface is used by classes which are created to validate the Verifiable Object.
 *
 */
public interface Verifier<T extends Verifiable> {
    void verify(T t) throws VerificationException;
    void verifyUnit(T t) throws VerificationException;
}
