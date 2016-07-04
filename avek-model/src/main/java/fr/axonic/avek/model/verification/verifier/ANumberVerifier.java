package fr.axonic.avek.model.verification.verifier;


import fr.axonic.avek.model.base.ANumber;
import fr.axonic.avek.model.verification.Verifier;
import fr.axonic.avek.model.verification.exception.*;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lbrouchet on 22/07/2015.
 */
public class ANumberVerifier implements Verifier<ANumber> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ANumberVerifier.class);

    @Override
    public void verify(ANumber aNumber) throws VerificationException {
        LOGGER.trace("start verify");
        VerifiableError verifError = new VerifiableError();
        if (aNumber != null) {
            if (aNumber.getValue() != null) {
                if (aNumber.getMin() == null) {
                    LOGGER.trace("min null");
                    verifError.addSingleObjectError(new Pair<>(TechnicalErrorCategory.NULL_PARAMETER, aNumber));
                } else if (aNumber.getMin().doubleValue() > aNumber.doubleValue()) {
                    verifError.addSingleObjectError(new Pair<>(ANumberErrorCategory.MIN_NOT_RESPECTED, aNumber));
                }
                if (aNumber.getMax() == null) {
                    LOGGER.trace("max null");
                    verifError.addSingleObjectError(new Pair<>(TechnicalErrorCategory.NULL_PARAMETER, aNumber));
                } else if (aNumber.getMax().doubleValue() < aNumber.doubleValue()) {
                    verifError.addSingleObjectError(new Pair<>(ANumberErrorCategory.MAX_NOT_RESPECTED, aNumber));
                }
            } else {
                LOGGER.trace("value null");
                verifError.addSingleObjectError(new Pair<>(AVarErrorCategory.NULL_VALUE, aNumber));
            }
            try {
                new AVarVerifier().verify(aNumber);
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
    public void verifyUnit(ANumber aNumber) throws VerificationException {
        LOGGER.trace("start verifyUnit");
        VerifiableError verifError = new VerifiableError();
        if (aNumber != null) {
            if (aNumber.getValue() != null) {
                if (aNumber.getMin() == null) {} else if (aNumber.getMin().doubleValue() > aNumber.doubleValue()) {
                    LOGGER.trace("- aNumber.getMin().doubleValue() : " + aNumber.getMin().doubleValue());
                    LOGGER.trace("- aNumber.doubleValue() : " + aNumber.doubleValue());
                    verifError.addSingleObjectError(new Pair<>(ANumberErrorCategory.MIN_NOT_RESPECTED, aNumber));
                }
                if (aNumber.getMax() == null) {} else if (aNumber.getMax().doubleValue() < aNumber.doubleValue()) {
                    LOGGER.trace("- aNumber.getMax().doubleValue() : " + aNumber.getMax().doubleValue());
                    LOGGER.trace("- aNumber.doubleValue() : " + aNumber.doubleValue());
                    verifError.addSingleObjectError(new Pair<>(ANumberErrorCategory.MAX_NOT_RESPECTED, aNumber));
                }
            }
            if (aNumber.getMin() != null && aNumber.getMax() != null) {
                if (aNumber.getMin().doubleValue() > aNumber.getMax().doubleValue()) {
                    verifError.addSingleObjectError(new Pair<>(ANumberErrorCategory.WRONG_MIN_MAX, aNumber));
                }
            }
            try {
                new AVarVerifier().verifyUnit(aNumber);
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
