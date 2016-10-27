package fr.axonic.base;

import fr.axonic.base.engine.AVar;
import fr.axonic.validation.Verify;
import fr.axonic.validation.aspects.VerificationMapper;
import fr.axonic.validation.exception.VerificationException;
import fr.axonic.verifier.AContinuousNumberVerifier;
import fr.axonic.verifier.ARangedEnumVerifier;
import fr.axonic.verifier.ARangedStringVerifier;
import fr.axonic.verifier.AStringVerifier;
import fr.axonic.verifier.AVarVerifier;

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

    void around(ARangedString aString, boolean verifyConsistency) throws VerificationException : target(aString) &&
                    execution(@Verify * ARangedString.verify(boolean)) && args(verifyConsistency) {
        VerificationMapper.verifyObject(verifyConsistency, new ARangedStringVerifier(), aString);
    }
}
