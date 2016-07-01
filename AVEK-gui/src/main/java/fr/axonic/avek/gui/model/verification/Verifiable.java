package fr.axonic.avek.gui.model.verification;


import fr.axonic.avek.gui.model.verification.exception.VerificationException;

/**
 * This class is the entry point to aspect weaving for validation purposes
 */
public interface Verifiable {
    /**
     * This method has to be empty. In any case the code will be ignore. The annotation @Verify will dynamically add what is necessary to verify
     * @see fr.axonic.core.validation.aspects.Verify
     * @param verifyConsistency the boolean is used to specify if you want to do an unit verification or also a coherence verification
     * @throws VerificationException
     */
    void verify(boolean verifyConsistency) throws VerificationException;
}
