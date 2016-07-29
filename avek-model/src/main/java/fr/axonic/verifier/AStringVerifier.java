package fr.axonic.verifier;


import fr.axonic.base.AString;
import fr.axonic.validation.Verifier;
import fr.axonic.validation.exception.ErrorVerifyException;
import fr.axonic.validation.exception.TechnicalErrorCategory;
import fr.axonic.validation.exception.VerifiableError;
import fr.axonic.validation.exception.VerificationException;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cduffau on 08/07/16.
 */
public class AStringVerifier implements Verifier<AString> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AStringVerifier.class);

    @Override
    public void verify(AString aString) throws VerificationException {
        LOGGER.trace("start verify");
        VerifiableError verifError = new VerifiableError();
        if (aString != null) {
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
    public void verifyUnit(AString aString) throws VerificationException {
        LOGGER.trace("start verifyUnit");
        VerifiableError verifError = new VerifiableError();
        if (aString != null) {
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
