package fr.axonic.validation;


import fr.axonic.validation.exception.VerificationException;

/**
 * This class is the entry point to aspect weaving for validation purposes
 */
public interface Verifiable {
    /**
     * This method has to be empty. In any case the code will be ignore. The annotation @Verify will dynamically add what is necessary to verify
     * @see fr.axonic.validation.Verify
     * @param verifyConsistency the boolean is used to specify if you want to do an unit fr.axonic.validation or also a coherence fr.axonic.validation
     * @throws VerificationException
     */
    void verify(boolean verifyConsistency) throws VerificationException;
}
