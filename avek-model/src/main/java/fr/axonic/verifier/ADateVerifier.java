package fr.axonic.verifier;


import fr.axonic.base.ADate;
import fr.axonic.validation.Verifier;
import fr.axonic.validation.exception.VerificationException;

/**
 * Created by lbrouchet on 22/07/2015.
 */
public class ADateVerifier implements Verifier<ADate> {

    @Override
    public void verify(ADate aDate) throws VerificationException {
        // NOTHING YET
    }

    @Override
    public void verifyUnit(ADate aDate) throws VerificationException {
        // NOTHING YET
    }
}
