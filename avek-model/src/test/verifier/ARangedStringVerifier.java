package fr.axonic.verifier;

import fr.axonic.base.ARangedString;
import fr.axonic.base.engine.AVarHelper;
import fr.axonic.validation.Verifier;
import fr.axonic.validation.exception.*;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cduffau on 08/07/16.
 */
public class ARangedStringVerifier implements Verifier<ARangedString> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ARangedStringVerifier.class);

    @Override
    public void verify(ARangedString aString) throws VerificationException {
        LOGGER.trace("start verify");
        VerifiableError verifError = new VerifiableError();
        if (aString != null) {
            if (aString.getValue() != null) {
                if (aString.getRange() == null) {
                    LOGGER.trace("range null");
                    verifError.addSingleObjectError(new Pair<>(TechnicalErrorCategory.NULL_PARAMETER, aString));
                } else if (!AVarHelper.containsByValue(aString.getRange(), aString)) {
                    verifError.addSingleObjectError(new Pair<>(AEnumErrorCategory.RANGE_NOT_RESPECTED, aString));
                }
            } else {
                LOGGER.trace("value null");
                verifError.addSingleObjectError(new Pair<>(AVarErrorCategory.NULL_VALUE, aString));
            }
            try {
                new AVarVerifier().verify(aString);
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
    public void verifyUnit(ARangedString aString) throws VerificationException {
        LOGGER.trace("start verifyUnit");
        VerifiableError verifError = new VerifiableError();
        if (aString != null) {
            if (aString.getValue() != null) {
                if (aString.getRange() == null) {} else if (!AVarHelper.containsByValue(aString.getRange(), aString)) {
                    LOGGER.trace("- aString.getRangeList() : " + aString.getRange());
                    LOGGER.trace("- aString.stringValue() : " + aString.getValue());
                    verifError.addSingleObjectError(new Pair<>(AEnumErrorCategory.RANGE_NOT_RESPECTED, aString));
                }
            }
            try {
                new AVarVerifier().verifyUnit(aString);
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
