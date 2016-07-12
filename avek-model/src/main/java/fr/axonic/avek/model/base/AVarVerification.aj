package fr.axonic.avek.model.base;

import fr.axonic.avek.model.verification.Verify;
import fr.axonic.avek.model.verification.aspects.VerificationMapper;
import fr.axonic.avek.model.verification.exception.VerificationException;
import fr.axonic.avek.model.verification.verifier.AContinuousNumberVerifier;
import fr.axonic.avek.model.verification.verifier.ARangedEnumVerifier;
import fr.axonic.avek.model.verification.verifier.AVarVerifier;
import fr.axonic.avek.model.verification.verifier.AStringVerifier;
import fr.axonic.avek.model.base.engine.AVar;

public aspect AVarVerification {

    // MAPPING util.base package (AVar) //

    void around(AContinuousNumber aNumber, boolean verifyConsistency) throws VerificationException : target(aNumber) &&
                  execution(@Verify * AContinuousNumber.verify(boolean)) && args(verifyConsistency) {
        VerificationMapper.verifyObject(verifyConsistency, new AContinuousNumberVerifier(), aNumber);
    }

    void around(AVar aVar, boolean verifyConsistency) throws VerificationException : target(aVar) &&
                  execution(@Verify * AVar.verify(boolean)) && args(verifyConsistency) {
        VerificationMapper.verifyObject(verifyConsistency, new AVarVerifier(), aVar);
    }

    void around(ARangedEnum aEnum, boolean verifyConsistency) throws VerificationException : target(aEnum) &&
                      execution(@Verify * ARangedEnum.verify(boolean)) && args(verifyConsistency) {
            VerificationMapper.verifyObject(verifyConsistency, new ARangedEnumVerifier(), aEnum);
    }

    void around(AString aString, boolean verifyConsistency) throws VerificationException : target(aString) &&
                      execution(@Verify * AString.verify(boolean)) && args(verifyConsistency) {
            VerificationMapper.verifyObject(verifyConsistency, new AStringVerifier(), aString);
    }
}
