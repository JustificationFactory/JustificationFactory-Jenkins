package fr.axonic.avek.model.verification.verifier;

import fr.axonic.avek.model.base.AEnum;
import fr.axonic.avek.model.base.ARangedEnum;
import fr.axonic.avek.model.verification.Verifier;
import fr.axonic.avek.model.verification.exception.*;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cduffau on 08/07/16.
 */
public class ARangedEnumVerifier implements Verifier<ARangedEnum>{

    private static final Logger LOGGER = LoggerFactory.getLogger(ARangedEnumVerifier.class);

    @Override
    public void verify(ARangedEnum aEnum) throws VerificationException {
        LOGGER.trace("start verify");
        VerifiableError verifError = new VerifiableError();
        if (aEnum != null) {
            if (aEnum.getValue() != null) {
                if (aEnum.getValue() == null) {
                    LOGGER.trace("range null");
                    verifError.addSingleObjectError(new Pair<>(TechnicalErrorCategory.NULL_PARAMETER, aEnum));
                } else if (!aEnum.getRange().contains(aEnum.getValue())) {
                    verifError.addSingleObjectError(new Pair<>(AEnumErrorCategory.RANGE_NOT_RESPECTED, aEnum));
                }
            } else {
                LOGGER.trace("value null");
                verifError.addSingleObjectError(new Pair<>(AVarErrorCategory.NULL_VALUE, aEnum));
            }
            try {
                new AVarVerifier().verify(aEnum);
            } catch (VerificationException e) {
                verifError.addErrors(e.getErrors());
            }
        } else {
            verifError.addSingleObjectError(new Pair<>(TechnicalErrorCategory.NULL_OBJECT, null));
        }
        LOGGER.trace("end verify");
        if (!verifError.isEmpty())
            throw new ErrorVerifyException(verifError);
    }

    @Override
    public void verifyUnit(ARangedEnum aEnum) throws VerificationException {
        LOGGER.trace("start verifyUnit");
        VerifiableError verifError = new VerifiableError();
        if (aEnum != null) {
            if (aEnum.getValue() != null) {
                if (aEnum.getRange() == null) {} else if (!aEnum.getRange().contains(aEnum.getValue())) {
                    LOGGER.trace("- aEnum.getEnumsRange() : " + aEnum.getRange());
                    LOGGER.trace("- aEnum.enumValue() : " + aEnum.getValue());
                    verifError.addSingleObjectError(new Pair<>(AEnumErrorCategory.RANGE_NOT_RESPECTED, aEnum));
                }
            }
            try {
                new AVarVerifier().verifyUnit(aEnum);
            } catch (VerificationException e) {
                verifError.addErrors(e.getErrors());
            }
        } else {
            verifError.addSingleObjectError(new Pair<>(TechnicalErrorCategory.NULL_OBJECT, null));
        }
        LOGGER.trace("end verifyUnit");
        if (!verifError.isEmpty())
            throw new ErrorVerifyException(verifError);
    }
}
