package fr.axonic.avek.model.verification;

import fr.axonic.avek.model.base.ANumber;
import fr.axonic.avek.model.base.AVar;
import fr.axonic.avek.model.base.Format;
import fr.axonic.avek.model.base.FormatType;
import fr.axonic.avek.model.verification.exception.ErrorVerifyException;
import fr.axonic.avek.model.verification.exception.VerificationException;
import org.junit.Test;

/**
 * Created by cduffau on 05/07/16.
 */
public class AVarVerifierTest {

    @Test
    public void testGoodRangeVerifier() throws VerificationException {
        ANumber aNumber= (ANumber) AVar.create(new Format(FormatType.NUMBER));
        aNumber.setMax(10);
        aNumber.setMin(1);

        aNumber.setValue(5);
    }

    @Test(expected = ErrorVerifyException.class)
    public void testWrongRangeVerifier() throws VerificationException {
        ANumber aNumber= (ANumber) AVar.create(new Format(FormatType.NUMBER));
        aNumber.setMax(10);
        aNumber.setMin(1);
        aNumber.setValue(0);
    }
}
