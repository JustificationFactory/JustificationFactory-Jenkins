package fr.axonic.observation.binding;


import fr.axonic.base.ANumber;
import fr.axonic.base.engine.AStructure;
import fr.axonic.validation.exception.VerificationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cboinaud on 19/07/2016.
 */
public class AStructureSimpleBindingTest {

    private BindingTestStructure toBeBinded, toBeBindedWith;

    @Before
    public void cleanUp(){
        toBeBinded = new BindingTestStructure(0, 0);
        toBeBindedWith = new BindingTestStructure(10, 20);
    }

    @After
    public void tearDown(){
        toBeBinded = null;
        toBeBindedWith = null;
    }

    @Test
    public void aStructureSimpleBindingTest() throws BindingTypesException, VerificationException {
        assertNotEquals(toBeBinded, toBeBindedWith);
        toBeBinded.bind(toBeBindedWith);

        assertEquals(toBeBinded.myNumber.getValue(), 10);
        assertEquals(toBeBinded.myStructure.myNumber.getValue(), 20);

        toBeBindedWith.myNumber.setValue(30);
        toBeBindedWith.myStructure.myNumber.setValue(40);

        assertEquals(toBeBinded.myNumber.getValue(), 30);
        assertEquals(toBeBinded.myStructure.myNumber.getValue(), 40);

        assertTrue(toBeBinded.isBindWith(toBeBindedWith));
        assertFalse(toBeBindedWith.isBindWith(toBeBinded));
    }

    @Test
    public void aStructureUnbindSimpleBindingTest() throws BindingTypesException, VerificationException {
        assertNotEquals(toBeBinded, toBeBindedWith);

        toBeBinded.bind(toBeBindedWith);
        assertEquals(toBeBinded, toBeBindedWith);

        toBeBinded.unbind();
        assertEquals(toBeBinded, toBeBindedWith);

        toBeBindedWith.myNumber.setValue(11);
        assertNotEquals(toBeBinded, toBeBindedWith);

        assertFalse(toBeBinded.isBindWith(toBeBindedWith));
        assertFalse(toBeBindedWith.isBindWith(toBeBinded));
    }

    @Test(expected = BindingTypesException.class)
    public void aStructureTypeExceptionSimpleBindingTest() throws BindingTypesException {
        BindingTestSubStructure structure = new BindingTestSubStructure(10);
        assertNotEquals(structure, toBeBinded);

        toBeBinded.bind(structure);
    }

    private static class BindingTestStructure extends AStructure {

        public ANumber myNumber;
        public BindingTestSubStructure myStructure;

        public BindingTestStructure(int x, int y) {
            myNumber = new ANumber(x);
            myStructure = new BindingTestSubStructure(y);
            init();
        }
    }

    private static class BindingTestSubStructure extends AStructure {

        public ANumber myNumber;

        public BindingTestSubStructure(int n){
            myNumber = new ANumber(n);
            init();
        }
    }

}
