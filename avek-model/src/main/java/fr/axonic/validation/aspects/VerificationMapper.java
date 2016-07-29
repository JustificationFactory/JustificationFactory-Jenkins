package fr.axonic.validation.aspects;


import fr.axonic.validation.Verifiable;
import fr.axonic.validation.Verifier;
import fr.axonic.validation.exception.VerificationException;

/**
 * Created by lbrouchet on 22/07/2015.
 * This class is used to do the mapping between the verify method call on Verifiable Object and the verify method
 * from the Validation system.
 *
 */
public class VerificationMapper {

    public static void verifyObject(boolean verifyConsistency, Verifier verifyObject, Verifiable verifiable)
            throws VerificationException {
        if (verifyConsistency) {
            verifyObject.verify(verifiable);
        } else {
            verifyObject.verifyUnit(verifiable);
        }
    }
}
