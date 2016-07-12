package fr.axonic.avek.model.verification;

import fr.axonic.avek.model.base.*;
import fr.axonic.avek.model.verification.exception.ErrorVerifyException;
import fr.axonic.avek.model.verification.exception.VerificationException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by cduffau on 08/07/16.
 */
public class AEnumVerifierTest {

    enum TestEnum{
        A,B,C,D;
    }

    @Test
    public void testGoodRangeVerifier() throws VerificationException {
        ARangedEnum<TestEnum> aNumber= (ARangedEnum<TestEnum>) AVar.create(new Format(FormatType.RANGED_ENUM));
        TestEnum[] range=TestEnum.values();
        range=Arrays.copyOfRange(range,0,2);
        aNumber.setRange(Arrays.asList(range));
        aNumber.setValue(TestEnum.A);
    }

    @Test(expected = ErrorVerifyException.class)
    public void testWrongRangeVerifier() throws VerificationException {
        ARangedEnum<TestEnum> aNumber= (ARangedEnum<TestEnum>) AVar.create(new Format(FormatType.RANGED_ENUM));
        TestEnum[] range=TestEnum.values();
        range=Arrays.copyOfRange(range,0,2);
        aNumber.setRange(Arrays.asList(range));
        aNumber.setValue(TestEnum.D);
    }
    @Test
    public void testGoodVerifier() throws VerificationException {
        ARangedEnum<TestEnum> aNumber= (ARangedEnum<TestEnum>) AVar.create(new Format(FormatType.RANGED_ENUM));
        TestEnum[] range=TestEnum.values();
        range=Arrays.copyOfRange(range,0,2);
        aNumber.setRange(Arrays.asList(range));
        aNumber.setValue(TestEnum.A);
        aNumber.verify(false);
    }

    @Test(expected = ErrorVerifyException.class)
    public void testWrongVerifier() throws VerificationException {
        ARangedEnum<TestEnum> aNumber= (ARangedEnum<TestEnum>) AVar.create(new Format(FormatType.RANGED_ENUM));
        TestEnum[] range=TestEnum.values();
        range=Arrays.copyOfRange(range,0,2);
        try {
            aNumber.setRange(Arrays.asList(range));
        } catch (VerificationException e) {
            fail();
        }
        aNumber.verify(true);
    }

    @Test
    public void testGoodVerifier2() throws VerificationException {
        ARangedEnum<TestEnum> aNumber= (ARangedEnum<TestEnum>) AVar.create(new Format(FormatType.RANGED_ENUM));
        aNumber.setCode("test");
        aNumber.setPath("test.test");
        aNumber.setRange(Arrays.asList(TestEnum.values()));
        aNumber.setDefaultValue(TestEnum.A);
        assertEquals(aNumber.getValue(),TestEnum.A);
        aNumber.setValue(TestEnum.B);
        assertEquals(aNumber.getValue(),TestEnum.B);
        aNumber.verify(true);
    }

    @Test
    public void testGoodVerifier3() throws VerificationException {
        ARangedEnum<TestEnum> aNumber= (ARangedEnum<TestEnum>) AVar.create(new Format(FormatType.RANGED_ENUM));
        aNumber.setCode("test");
        aNumber.setPath("test.test");
        aNumber.setRange(Arrays.asList(TestEnum.values()));
        aNumber.setValue(TestEnum.A);
        assertEquals(aNumber.getValue(),TestEnum.A);
        aNumber.setDefaultValue(TestEnum.B);
        assertEquals(aNumber.getValue(),TestEnum.A);
        aNumber.verify(true);
    }
}
