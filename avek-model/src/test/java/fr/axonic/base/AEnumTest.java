package fr.axonic.base;

import fr.axonic.TestEnum;
import fr.axonic.base.engine.AVar;
import fr.axonic.base.engine.AVarProperty;
import fr.axonic.base.format.EnumFormat;
import fr.axonic.base.format.Format;
import fr.axonic.base.engine.FormatType;
import fr.axonic.base.format.RangedEnumFormat;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by cduffau on 08/07/16.
 */
public class AEnumTest {


    @Test
    public void testValue() throws VerificationException {
        AEnum<TestEnum> aNumber= (AEnum<TestEnum>) AVar.create(new EnumFormat());
        aNumber.setValue(TestEnum.A);
        assertEquals(aNumber.getValue(),TestEnum.A);
    }

    @Test
    public void testVerifiableProperties() throws VerificationException {
        ARangedEnum<TestEnum> aNumber= (ARangedEnum<TestEnum>) AVar.create(new RangedEnumFormat());
        aNumber.setValue(TestEnum.A);
        assertTrue(aNumber.isPropertyVerifiable(AVarProperty.RANGE.name()));
        assertTrue(aNumber.isPropertyVerifiable(AVarProperty.DEFAULT_VALUE.name()));
        assertTrue(aNumber.isPropertyVerifiable(AVarProperty.VALUE.name()));
    }

    @Test
    public void testClone() throws VerificationException, CloneNotSupportedException {
        AEnum<TestEnum> aNumber= (AEnum<TestEnum>) AVar.create(new EnumFormat());
        aNumber.setValue(TestEnum.A);
        AVar aNumber2=aNumber.clone();
        assertEquals(aNumber,aNumber2);
    }
}
