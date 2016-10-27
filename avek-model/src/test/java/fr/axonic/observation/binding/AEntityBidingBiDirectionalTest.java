package fr.axonic.observation.binding;


import fr.axonic.base.ANumber;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AStructure;
import fr.axonic.validation.exception.VerificationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cboinaud on 20/07/2016.
 */
public class AEntityBidingBiDirectionalTest {

    ANumber number1, number2;
    BiDirBindTestStructure structure1, structure2;
    AList<AString> list1, list2;

    @Before
    public void cleanUp(){
        number1 = new ANumber(1);
        number2 = new ANumber(2);

        structure1 = new BiDirBindTestStructure(1,2);
        structure2 = new BiDirBindTestStructure(3, 4);

        list1 = new AList<>();
        list2 = new AList<AString>() {{
            add(new AString("aaa"));
            add(new AString("bbb"));
            add(new AString("ccc"));
            add(new AString("bbb"));
        }};
    }

    @After
    public void tearDown(){
        number1.removeListeners();
        number1 = null;
        number2.removeListeners();
        number2 = null;

        structure1.removeListeners();
        structure1 = null;
        structure2.removeListeners();
        structure2 = null;

        list1.removeListeners();
        list1 = null;
        list2.removeListeners();
        list2 = null;
    }

    @Test
    public void aVarBiDirectionalBindTest()
            throws BindingTypesException, VerificationException, BindingParametersException {
        assertNotEquals(number1, number2);

        number1.bindBidirectional(number2);
        assertEquals(number1, number2);

        number1.setValue(10);
        assertEquals(number2.getValue(), 10);
        assertEquals(number1, number2);

        number2.setValue(20);
        assertEquals(number1.getValue(), 20);
        assertEquals(number1, number2);

        assertTrue(number1.isBindWith(number2));
        assertTrue(number2.isBindWith(number1));
    }

    @Test
    public void aVarBiDirectionalUnbindTest()
            throws BindingTypesException, VerificationException, BindingParametersException {
        number1.bindBidirectional(number2);
        assertEquals(number1, number2);

        number2.unbind();

        number1.setValue(10);
        assertEquals(number1.getValue(), 10);
        assertNotEquals(number2.getValue(), 10);

        number2.setValue(20);
        assertEquals(number2.getValue(), 20);
        assertNotEquals(number1.getValue(), 20);

        assertFalse(number1.isBindWith(number2));
        assertFalse(number2.isBindWith(number1));

        number1.bindBidirectional(number2);
        assertEquals(number1, number2);

        number1.unbind();

        assertFalse(number1.isBindWith(number2));
        assertFalse(number2.isBindWith(number1));
    }

    @Test
    public void aStructureBiDirectionalBindTest()
            throws BindingTypesException, VerificationException, BindingParametersException {
        assertNotEquals(structure1, structure2);

        structure1.bindBidirectional(structure2);
        assertEquals(structure1, structure2);

        structure1.myNumber.setValue(10);
        assertEquals(structure2.myNumber.getValue(), 10);
        assertEquals(structure1, structure2);

        structure2.myNumber.setValue(20);
        assertEquals(structure1.myNumber.getValue(), 20);
        assertEquals(structure1, structure2);

        assertTrue(structure1.isBindWith(structure2));
        assertTrue(structure2.isBindWith(structure1));
    }

    @Test
    public void aStructureBiDirectionalUnbindTest()
            throws BindingTypesException, VerificationException, BindingParametersException {
        structure1.bindBidirectional(structure2);
        assertEquals(structure1, structure2);

        structure2.unbind();

        structure1.myNumber.setValue(10);
        assertEquals(structure1.myNumber.getValue(), 10);
        assertNotEquals(structure2.myNumber.getValue(), 10);

        structure2.myNumber.setValue(20);
        assertEquals(structure2.myNumber.getValue(), 20);
        assertNotEquals(structure1.myNumber.getValue(), 20);

        assertFalse(structure1.isBindWith(structure2));
        assertFalse(structure2.isBindWith(structure1));

        structure1.bindBidirectional(structure2);
        assertEquals(structure1, structure2);

        structure1.unbind();

        assertFalse(structure1.isBindWith(structure2));
        assertFalse(structure2.isBindWith(structure1));
    }

    @Test
    public void aListBiDirectionalBindTest()
            throws BindingTypesException, VerificationException, BindingParametersException {
        assertNotEquals(list1, list2);

        list1.bindBidirectional(list2);
        assertEquals(list1, list2);

        list1.add(new AString("zzz"));
        assertTrue(list2.contains(new AString("zzz")));
        assertEquals(list1, list2);

        list2.remove(new AString("zzz"));
        assertFalse(list1.contains(new AString("zzz")));
        assertEquals(list1, list2);

        assertTrue(list1.isBindWith(list2));
        assertTrue(list2.isBindWith(list1));
    }

    @Test
    public void aListBiDirectionalUnbindTest()
            throws BindingParametersException, BindingTypesException, VerificationException {

        list1.bindBidirectional(list2);
        assertEquals(list1, list2);

        list2.unbind();

        list1.add(new AString("zzz"));
        assertTrue(list1.contains(new AString("zzz")));
        assertFalse(list2.contains(new AString("zzz")));

        list2.add(new AString("xxx"));
        assertTrue(list2.contains(new AString("xxx")));
        assertFalse(list1.contains(new AString("xxx")));

        assertFalse(list1.isBindWith(list2));
        assertFalse(list2.isBindWith(list1));

        list1.bindBidirectional(list2);
        assertEquals(list1, list2);

        list1.unbind();

        assertFalse(list1.isBindWith(list2));
        assertFalse(list2.isBindWith(list1));
    }

    private static class BiDirBindTestStructure extends AStructure {

        public final ANumber myNumber;
        public final BiDirBindTestSubStructure myStructure;

        public BiDirBindTestStructure(int x, int y) {
            myNumber = new ANumber(x);
            myStructure = new BiDirBindTestSubStructure(y);
            init();
        }
    }

    private static class BiDirBindTestSubStructure extends AStructure {

        public final ANumber myNumber;

        public BiDirBindTestSubStructure(int n){
            myNumber = new ANumber(n);
            init();
        }
    }

}
