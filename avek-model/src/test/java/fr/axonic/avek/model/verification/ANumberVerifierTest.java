package fr.axonic.avek.model.verification;

import fr.axonic.avek.model.base.ANumber;
import fr.axonic.avek.model.base.AVar;
import fr.axonic.avek.model.base.Format;
import fr.axonic.avek.model.base.FormatType;
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
    @Test
    public void testGoodVerifier() throws VerificationException {
        ANumber aNumber= (ANumber) AVar.create(new Format(FormatType.NUMBER));
        aNumber.setMax(10);
        aNumber.setMin(1);

        aNumber.setValue(5);
        aNumber.verify(false);
    }

    @Test(expected = ErrorVerifyException.class)
    public void testWrongVerifier() throws VerificationException {
        ANumber aNumber= (ANumber) AVar.create(new Format(FormatType.NUMBER));
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
        ANumber aNumber= (ANumber) AVar.create(new Format(FormatType.NUMBER));
        aNumber.setCode("test");
        aNumber.setPath("test.test");
        aNumber.setMax(10);
        aNumber.setMin(1);
        aNumber.setDefaultValue(2);
        assertEquals(aNumber.intValue(),2);
        aNumber.setValue(5);
        assertEquals(aNumber.intValue(),5);
        aNumber.verify(true);
    }

    @Test
    public void testGoodVerifier3() throws VerificationException {
        ANumber aNumber= (ANumber) AVar.create(new Format(FormatType.NUMBER));
        aNumber.setCode("test");
        aNumber.setPath("test.test");
        aNumber.setMax(10);
        aNumber.setMin(1);
        aNumber.setValue(5);
        assertEquals(aNumber.intValue(),5);
        aNumber.setDefaultValue(2);
        assertEquals(aNumber.intValue(),5);
        aNumber.verify(true);
    }
}
