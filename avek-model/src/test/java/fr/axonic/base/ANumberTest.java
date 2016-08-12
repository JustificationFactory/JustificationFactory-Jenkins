package fr.axonic.base;

import fr.axonic.base.engine.AVar;
import fr.axonic.base.engine.AVarProperty;
import fr.axonic.base.format.BoundedNumberFormat;
import fr.axonic.base.format.Format;
import fr.axonic.base.engine.FormatType;
import fr.axonic.base.format.NumberFormat;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 07/07/16.
 */
public class ANumberTest {

    @Test
    public void testValue() throws VerificationException {
        ANumber aNumber= (ANumber) AVar.create(new NumberFormat());
        aNumber.setValue(1.0);
        assertEquals(aNumber.getValue().intValue(),1);
        assertEquals(aNumber.getValue().floatValue(),1.0f,0);
        assertEquals(aNumber.getValue().doubleValue(),1.0d,0);
        assertEquals(aNumber.getValue().longValue(),1L);
    }

    @Test
    public void testVerifiableProperties() throws VerificationException {
        AContinuousNumber aNumber= (AContinuousNumber) AVar.create(new BoundedNumberFormat());
        aNumber.setValue(1.0);
        assertTrue(aNumber.isPropertyVerifiable(AVarProperty.MIN.name()));
        assertTrue(aNumber.isPropertyVerifiable(AVarProperty.MAX.name()));
        assertTrue(aNumber.isPropertyVerifiable(AVarProperty.DEFAULT_VALUE.name()));
        assertTrue(aNumber.isPropertyVerifiable(AVarProperty.VALUE.name()));
    }

    @Test
    public void testClone() throws VerificationException, CloneNotSupportedException {
        AVar aNumber= AVar.create(new NumberFormat());
        aNumber.setValue(1.0);
        AVar aNumber2=aNumber.clone();
        assertEquals(aNumber,aNumber2);
    }



}
