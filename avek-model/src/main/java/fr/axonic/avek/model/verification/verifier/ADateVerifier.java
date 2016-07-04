package fr.axonic.avek.model.verification.verifier;


import fr.axonic.avek.model.base.ADate;
import fr.axonic.avek.model.verification.Verifier;
import fr.axonic.avek.model.verification.exception.VerificationException;

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
