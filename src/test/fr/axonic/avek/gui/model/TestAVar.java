package fr.axonic.avek.gui.model;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Nathaël N on 30/06/16.
 */
public class TestAVar {



	@Test
	public void testCorrectValueTypes() {
		int knb = 1;

		// Same type ?
		try{ new AVar("key"+(knb++), Integer.class, 0); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }
		try{ new AVar("key"+(knb++), Integer.class, -1); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }
		try{ new AVar("key"+(knb++), Integer.class, Integer.MAX_VALUE); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }
		try{ new AVar("key"+(knb++), Integer.class, Integer.MIN_VALUE); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }
		try{ new AVar("key"+(knb++), Integer.class, 42); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }

		try{ new AVar("key"+(knb++), Double.class, 12.34); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }
		try{ new AVar("key"+(knb++), Double.class, -56.78); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }
		try{ new AVar("key"+(knb++), Double.class, 12d); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }
		try{ new AVar("key"+(knb++), Double.class, Double.MAX_VALUE); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }
		try{ new AVar("key"+(knb++), Double.class, Double.NaN); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }
		try{ new AVar("key"+(knb++), Double.class, Double.POSITIVE_INFINITY); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }
		try{ new AVar("key"+(knb++), Double.class, Double.NEGATIVE_INFINITY); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }

		try{ new AVar("key"+(knb++), String.class, ""); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }
		try{ new AVar("key"+(knb++), String.class, "Test"); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }
		try{ new AVar("key"+(knb++), String.class, "-1"); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }
		try{ new AVar("key"+(knb++), String.class, new Date().toString()); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }

		// Parent class ?
		try{ new AVar("key"+(knb++), Map.class, new HashMap()); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }

		// Generic class ?
		try{ new AVar("key"+(knb++), HashMap.class, new HashMap<String, Integer>()); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }

		// Class class ?
		try{ new AVar("key"+(knb++), Class.class, Class.class); assertTrue(true); } catch(ClassCastException c) { assertTrue(false); }

		// Interface ?
		try { new AVar("key" + (knb++), Iterable.class, new ArrayList()); assertTrue(true); }catch(ClassCastException c) { assertTrue(false); }
	}

	@Test
	public void testIncorrectValueTypes() {
		int knb = 1;

	/* // Compilation detected errors

		// Not the same type
		try { new AVar("key" + (knb++), Integer.class, new Double(42)); assertTrue(false); }catch(ClassCastException c) { assertTrue(true); }
		try { new AVar("key" + (knb++), Integer.class, 42.0); assertTrue(false); }catch(ClassCastException c) { assertTrue(true); }
		try { new AVar("key" + (knb++), Double.class, "42.31"); assertTrue(false); }catch(ClassCastException c) { assertTrue(true); }
		try { new AVar("key" + (knb++), String.class, 12345.6789); assertTrue(false); }catch(ClassCastException c) { assertTrue(true); }
		try { new AVar("key" + (knb++), Integer.class, new HashMap()); assertTrue(false); }catch(ClassCastException c) { assertTrue(true); }

		// Children class
		try { new AVar("key" + (knb++), LinkedHashMap.class, new HashMap()); assertTrue(false); }catch(ClassCastException c) { assertTrue(true); }

		// Sisters class
		try { new AVar("key" + (knb++), ArrayList.class, new LinkedList()); assertTrue(false); }catch(ClassCastException c) { assertTrue(true); }
	*/
	}
}
