package fr.axonic.avek.model.base;

import fr.axonic.avek.model.verification.Verify;
import fr.axonic.avek.model.verification.aspects.VerificationMapper;
import fr.axonic.avek.model.verification.exception.VerificationException;
import fr.axonic.avek.model.verification.verifier.ANumberVerifier;
import fr.axonic.avek.model.verification.verifier.AEnumVerifier;
import fr.axonic.avek.model.verification.verifier.AVarVerifier;

public aspect AVarVerification {

    // MAPPING util.base package (AVar) //

    void around(ANumber aNumber, boolean verifyConsistency) throws VerificationException : target(aNumber) &&
                  execution(@Verify * ANumber.verify(boolean)) && args(verifyConsistency) {
        VerificationMapper.verifyObject(verifyConsistency, new ANumberVerifier(), aNumber);
    }

    void around(AVar aVar, boolean verifyConsistency) throws VerificationException : target(aVar) &&
                  execution(@Verify * AVar.verify(boolean)) && args(verifyConsistency) {
        VerificationMapper.verifyObject(verifyConsistency, new AVarVerifier(), aVar);
    }
    void around(AEnum aEnum, boolean verifyConsistency) throws VerificationException : target(aEnum) &&
                      execution(@Verify * AEnum.verify(boolean)) && args(verifyConsistency) {
            VerificationMapper.verifyObject(verifyConsistency, new AEnumVerifier(), aEnum);
        }

}
