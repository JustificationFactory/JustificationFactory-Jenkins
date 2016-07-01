package fr.axonic.avek.gui.model.verification.aspects;


import fr.axonic.avek.gui.model.verification.Verifiable;
import fr.axonic.avek.gui.model.verification.Verifier;
import fr.axonic.avek.gui.model.verification.exception.VerificationException;

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
