package fr.axonic.avek.gui.model.verification.verifier;


import fr.axonic.avek.gui.model.base.AVar;
import fr.axonic.avek.gui.model.verification.Verifier;
import fr.axonic.avek.gui.model.verification.exception.*;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by lbrouchet on 23/07/2015.
 */
public class AVarVerifier implements Verifier<AVar> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AVarVerifier.class);

    @Override
    public void verify(AVar aVar) throws VerificationException {
        LOGGER.trace("start verify");
        VerifiableError verifError = new VerifiableError();
        if (aVar != null) {
            if (aVar.getValue() == null) {
                LOGGER.trace("value null");
                verifError.addSingleObjectError(new Pair<>(AVarErrorCategory.NULL_VALUE, aVar));
            }
            if (aVar.getPath() == null) {
                LOGGER.trace("path null");
                verifError.addSingleObjectError(new Pair<>(AVarErrorCategory.NULL_PATH, aVar));
            }
            if (aVar.getCode() == null) {
                LOGGER.trace("code null");
                verifError.addSingleObjectError(new Pair<>(AVarErrorCategory.NULL_CODE, aVar));
            }
            if (aVar.getFormat() == null) {
                LOGGER.trace("format null");
                verifError.addSingleObjectError(new Pair<>(AVarErrorCategory.NULL_FORMAT, aVar));
            }
        } else {
            verifError.addSingleObjectError(new Pair<>(TechnicalErrorCategory.NULL_OBJECT, null));
        }
        LOGGER.trace("end verify");
        if (!verifError.isEmpty())
            throw new ErrorVerifyException(verifError);
    }

    @Override
    public void verifyUnit(AVar aVar) throws VerificationException {
        LOGGER.trace("start verifyUnit");
        VerifiableError verifError = new VerifiableError();
        if (aVar == null) {
            verifError.addSingleObjectError(new Pair<>(TechnicalErrorCategory.NULL_OBJECT, null));
        }
        LOGGER.trace("end verifyUnit");
        if (!verifError.isEmpty())
            throw new ErrorVerifyException(verifError);
    }
}
