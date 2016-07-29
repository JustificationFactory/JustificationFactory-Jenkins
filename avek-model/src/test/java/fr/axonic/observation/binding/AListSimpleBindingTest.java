package fr.axonic.observation.binding;


import fr.axonic.base.AString;
import fr.axonic.base.engine.AList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by cboinaud on 19/07/2016.
 */
public class AListSimpleBindingTest {

    private AList<AString> listToBind, listToBeBindedWith;

    @Before
    public void cleanUp(){
        listToBind = new AList<>();
        listToBeBindedWith = new AList<AString>(){{
            add(new AString("aaa"));
            add(new AString("bbb"));
            add(new AString("ccc"));
            add(new AString("aaa"));
            add(new AString("ddd"));
            add(new AString("bbb"));
        }};
    }

    @After
    public void tearDown(){
        listToBind = null;
        listToBeBindedWith = null;
    }

    @Test
    public void aListSimpleBindingInitTest() throws BindingTypesException {
        assertNotEquals(listToBind, listToBeBindedWith);

        listToBind.bind(listToBeBindedWith);
        assertEquals(listToBind, listToBeBindedWith);

        assertTrue(listToBind.isBindWith(listToBeBindedWith));
        assertFalse(listToBeBindedWith.isBindWith(listToBind));
    }

    @Test
    public void aListSimpleBindingUnbindTest() throws BindingTypesException {
        assertNotEquals(listToBind, listToBeBindedWith);

        listToBind.bind(listToBeBindedWith);
        assertEquals(listToBind, listToBeBindedWith);

        listToBind.unbind();
        assertEquals(listToBind, listToBeBindedWith);

        listToBeBindedWith.set(0, new AString("zzz"));
        assertNotEquals(listToBind, listToBeBindedWith);
    }

    @Test
    public void aListAddSimpleBindingSingleAddTest() throws BindingTypesException {
        listToBind.bind(listToBeBindedWith);

        AString string = new AString("eee");

        listToBeBindedWith.add(string);
        assertTrue(listToBind.contains(string));

        assertEquals(listToBeBindedWith, listToBind);
    }

    @Test
    public void aListAddSimpleBindingMultipleAddTest1() throws BindingTypesException {
        listToBind.bind(listToBeBindedWith);

        Collection<AString> c = new ArrayList<AString>() {{
            add(new AString("eee"));
            add(new AString("fff"));
            add(new AString("ggg"));
            add(new AString("hhh"));
        }};

        listToBeBindedWith.addAll(c);
        assertTrue(listToBind.contains(new AString("eee")));
        assertTrue(listToBind.contains(new AString("hhh")));

        assertEquals(listToBeBindedWith, listToBind);
    }

    @Test
    public void aListAddSimpleBindingMultipleAddTest2() throws BindingTypesException {
        listToBind.bind(listToBeBindedWith);

        Collection<AString> c = new ArrayList<AString>() {{
            add(new AString("eee"));
            add(new AString("fff"));
            add(new AString("ggg"));
            add(new AString("hhh"));
        }};

        listToBeBindedWith.addAll(1, c);
        assertTrue(listToBind.contains(new AString("eee")));
        assertTrue(listToBind.contains(new AString("hhh")));

        assertEquals(listToBeBindedWith, listToBind);
    }

    @Test
    public void aListAddSimpleBindingSingleRemoveTest() throws BindingTypesException {
        listToBind.bind(listToBeBindedWith);

        listToBeBindedWith.remove(2);
        assertNotEquals(listToBind.get(2), new AString("ccc"));

        assertEquals(listToBeBindedWith, listToBind);

    }

    @Test
    public void aListAddSimpleBindingMultipleRemoveTest1() throws BindingTypesException {
        listToBind.bind(listToBeBindedWith);

        Collection<AString> c = new ArrayList<AString>() {{
            add(new AString("aaa"));
            add(new AString("bbb"));
        }};

        listToBeBindedWith.removeAll(c);
        assertFalse(listToBind.contains(new AString("aaa")));
        assertFalse(listToBind.contains(new AString("bbb")));

        assertEquals(listToBeBindedWith, listToBind);
    }

    @Test
    public void aListAddSimpleBindingMultipleRemoveTest2() throws BindingTypesException {
        listToBind.bind(listToBeBindedWith);

        Collection<AString> c = new ArrayList<AString>() {{
            add(new AString("ccc"));
            add(new AString("ddd"));
        }};

        listToBeBindedWith.retainAll(c);
        assertFalse(listToBind.contains(new AString("aaa")));
        assertFalse(listToBind.contains(new AString("bbb")));

        assertEquals(listToBeBindedWith, listToBind);
    }

    @Test
    public void aListAddSimpleBindingMultipleRemoveTest3() throws BindingTypesException {
        listToBind.bind(listToBeBindedWith);

        listToBeBindedWith.clear();
        assertTrue(listToBind.isEmpty());

        assertEquals(listToBeBindedWith, listToBind);
    }

    @Test
    public void aListAddSimpleBindingSingleChangeTest() throws BindingTypesException {
        listToBind.bind(listToBeBindedWith);

        listToBeBindedWith.set(2, new AString("zzz"));
        assertTrue(listToBind.contains(new AString("zzz")));

        assertEquals(listToBeBindedWith, listToBind);
    }

    @Test
    public void aListAddSimpleBindingMultipleChangeTest() throws BindingTypesException {
        listToBind.bind(listToBeBindedWith);

        listToBeBindedWith.replaceAll(elt -> new AString("zzz"));
        assertTrue(listToBind.contains(new AString("zzz")));

        assertEquals(listToBind.stream().filter(elt -> elt.equals(new AString("zzz"))).count(), listToBind.size());

        assertEquals(listToBeBindedWith, listToBind);
    }

    @Test
    public void aListAddSimpleBindingMultiplePermutationTest() throws BindingTypesException {
        listToBind.bind(listToBeBindedWith);

        listToBeBindedWith.sort((a,b) -> a.getValue().compareTo(b.getValue()));

        assertEquals(listToBind.indexOf(new AString("bbb")), 2);
        assertEquals(listToBind.indexOf(new AString("ccc")), 4);
        assertEquals(listToBind.indexOf(new AString("ddd")), 5);

        assertEquals(listToBeBindedWith, listToBind);
    }


}
