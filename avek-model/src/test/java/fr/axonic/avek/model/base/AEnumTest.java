package fr.axonic.avek.model.base;

import fr.axonic.avek.model.verification.exception.VerificationException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by cduffau on 08/07/16.
 */
public class AEnumTest {

    enum TestEnum{
        A,B,C,D;
    }


    @Test
    public void testValue() throws VerificationException {
        AEnum<TestEnum> aNumber= (AEnum<TestEnum>) AVar.create(new Format(FormatType.ENUM));
        aNumber.setValue(TestEnum.A);
        assertEquals(aNumber.getValue(),TestEnum.A);
    }

    @Test
    public void testVerifiableProperties() throws VerificationException {
        ARangedEnum<TestEnum> aNumber= (ARangedEnum<TestEnum>) AVar.create(new Format(FormatType.RANGED_ENUM));
        aNumber.setValue(TestEnum.A);
        assertTrue(aNumber.isPropertyVerifiable(AVarProperty.RANGE.name()));
        assertTrue(aNumber.isPropertyVerifiable(AVarProperty.DEFAULT_VALUE.name()));
        assertTrue(aNumber.isPropertyVerifiable(AVarProperty.VALUE.name()));
    }

    @Test
    public void testClone() throws VerificationException, CloneNotSupportedException {
        AEnum<TestEnum> aNumber= (AEnum<TestEnum>) AVar.create(new Format(FormatType.ENUM));
        aNumber.setValue(TestEnum.A);
        AVar aNumber2=aNumber.clone();
        assertEquals(aNumber,aNumber2);
    }
}
