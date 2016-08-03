package fr.axonic.avek.gui.model.json;

import fr.axonic.avek.gui.model.sample.BooleanState;
import fr.axonic.avek.gui.model.sample.ExampleState;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.base.*;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AVar;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nathaël N on 20/07/16.
 */
public class TestJsonifier {

	@Test
	public void testPrimitives() {
		test("Totoo");
		test(42.0);
		test(49.3);
		test(98765432123456789L);
	}

	@Ignore
	@Test
	public void testObjects() {
		// Array with unknown element number
		ArrayList<String> ls = new ArrayList<>();
		for (int i = (int) (100 * Math.random()); i > 0; i--) {
			ls.add((char) ('A' + Math.random() * ('Z' - 'A')) + "_" + Math.random());
		}

		test(ls);

		Jsonifier<ARangedEnum> jsonifier = new Jsonifier<>(ARangedEnum.class);

		ARangedEnum<ExampleState> rangedEnumState = new ARangedEnum<>(ExampleState.MEDIUM);
		ARangedEnum o2 = jsonifier.fromJson(Jsonifier.toJson(rangedEnumState));
		assertEquals(rangedEnumState.getValue().toString(), o2.getValue().toString());
		assertEquals(rangedEnumState.getRange(), o2.getRange());
		test2(rangedEnumState);

		MonitoredSystem ms = new MonitoredSystem(42);
		ms.addCategory("Cat1");
		ms.addCategory("Cat2");
		ms.addAVar("Cat1", rangedEnumState);
		ms.addAVar("Cat2", new ANumber(49.3));

		ARangedEnum<BooleanState> rangedEnumBool = new ARangedEnum<>(BooleanState.FALSE);
		o2 = jsonifier.fromJson(Jsonifier.toJson(rangedEnumBool));
		assertEquals(rangedEnumBool.getValue().toString(), o2.getValue().toString());
		assertEquals(rangedEnumBool.getRange(), o2.getRange());
		ms.addAVar("Cat2", rangedEnumBool);
		ms.addAVar("Cat1", new AString("Some AString"));

		test2(rangedEnumBool);

		String oJson = Jsonifier.toJson(ms);
		String o2Json = Jsonifier.toJson(new Jsonifier<>(MonitoredSystem.class).fromJson(oJson));
		assertEquals(oJson, o2Json);
	}

	@Ignore
	@Test
	public void testAListOfAEntities() {
		AList<AEntity> aList = new AList<>();

		aList.add(new ANumber("Frequency", 42.0));
		aList.add(new ABoolean("Bool?", true));

		AList<AEntity> list = new AList<>();
		list.setLabel("List");
		list.add(new ANumber("SubNumber", 12.34));
		list.add(new ADate("SubDate", new GregorianCalendar()));

		AList<AVar> subList = new AList<>();
		subList.setLabel("SubList");
		subList.add(new ANumber("SubSubInteger", 42.0));
		subList.add(new AString("SubSubString", "TheSubSubStringValue"));
		subList.add(new ANumber("SubSubDouble", 49.3));

		list.add(subList);
		list.add(new ABoolean("SubBool", false));
		list.setLabel("SubCategory");
		aList.add(list);

		aList.add(new ANumber("Times redo", 12.0));

		//String json = Jsonifier.toJson(aList);
		//AList<AEntity> regenerated = (AList<AEntity>) Jsonifier.toAEntity(json);

		test2(aList);
		//assertEquals(json, Jsonifier.toJson(regenerated));
	}


	private <T> void test(T o) {
		T o2 = new Jsonifier<>((Class<T>)o.getClass()).fromJson(Jsonifier.toJson(o));
		assertEquals(o, o2);
	}

	private <T extends AEntity> void test2(T o) {
		String oJson = Jsonifier.fromAEntity(o);
		String o2Json = Jsonifier.fromAEntity(Jsonifier.toAEntity(oJson));
		assertEquals(oJson, o2Json);
	}

}
