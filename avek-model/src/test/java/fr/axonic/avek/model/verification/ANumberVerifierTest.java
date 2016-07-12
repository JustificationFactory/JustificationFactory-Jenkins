package fr.axonic.avek.model.verification;

import fr.axonic.avek.model.base.*;
import fr.axonic.avek.model.base.engine.AVar;
import fr.axonic.avek.model.base.engine.Format;
import fr.axonic.avek.model.base.engine.FormatType;
import fr.axonic.avek.model.verification.exception.ErrorVerifyException;
import fr.axonic.avek.model.verification.exception.VerificationException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by cduffau on 05/07/16.
 */
public class ANumberVerifierTest {

    @Test
    public void testGoodRangeVerifier() throws VerificationException {
        AContinuousNumber aNumber= (AContinuousNumber) AVar.create(new Format(FormatType.RANGED_NUMBER));
        aNumber.setMax(10);
        aNumber.setMin(1);

        aNumber.setValue(5);
    }

    @Test(expected = ErrorVerifyException.class)
    public void testWrongRangeVerifier() throws VerificationException {
        AContinuousNumber aNumber= (AContinuousNumber) AVar.create(new Format(FormatType.RANGED_NUMBER));
        aNumber.setMax(10);
        aNumber.setMin(1);
        aNumber.setValue(0);
    }
    @Test
    public void testGoodVerifier() throws VerificationException {
        AContinuousNumber aNumber= (AContinuousNumber) AVar.create(new Format(FormatType.RANGED_NUMBER));
        aNumber.setMax(10);
        aNumber.setMin(1);

        aNumber.setValue(5);
        aNumber.verify(false);
    }

    @Test(expected = ErrorVerifyException.class)
    public void testWrongVerifier() throws VerificationException {
        AContinuousNumber aNumber= (AContinuousNumber) AVar.create(new Format(FormatType.RANGED_NUMBER));
        try {
            aNumber.setMax(10);
            aNumber.setMin(1);
        } catch (VerificationException e) {
            fail();
        }
        aNumber.verify(true);
    }

    @Test
    public void testGoodVerifier2() throws VerificationException {
        AContinuousNumber aNumber= (AContinuousNumber) AVar.create(new Format(FormatType.RANGED_NUMBER));
        aNumber.setCode("test");
        aNumber.setPath("test.test");
        aNumber.setMax(10);
        aNumber.setMin(1);
        aNumber.setDefaultValue(2);
        assertEquals(aNumber.getValue().intValue(),2);
        aNumber.setValue(5);
        assertEquals(aNumber.getValue().intValue(),5);
        aNumber.verify(true);
    }

    @Test
    public void testGoodVerifier3() throws VerificationException {
        AContinuousNumber aNumber= (AContinuousNumber) AVar.create(new Format(FormatType.RANGED_NUMBER));
        aNumber.setCode("test");
        aNumber.setPath("test.test");
        aNumber.setMax(10);
        aNumber.setMin(1);
        aNumber.setValue(5);
        assertEquals(aNumber.getValue().intValue(),5);
        aNumber.setDefaultValue(2);
        assertEquals(aNumber.getValue().intValue(),5);
        aNumber.verify(true);
    }
}
