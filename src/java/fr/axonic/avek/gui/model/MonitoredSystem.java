package fr.axonic.avek.gui.model;

import com.google.gson.Gson;

import java.util.*;

/**
 * Created by Nathaël N on 29/06/16.
 */
public class MonitoredSystem {
	private final int id;
	private final LinkedHashMap<String, LinkedHashSet<AVar>> lhm;

	public MonitoredSystem(int id) {
		this.id = id;
		this.lhm = new LinkedHashMap<>();
	}

	public LinkedHashSet<AVar> put(String key, LinkedHashSet<AVar> value) {
		return lhm.put(key, value);
	}

	public static void main(String[] args) {
		String json;
		Gson gson = new Gson(); // TODO This is for a test only

		{
			MonitoredSystem ms = new MonitoredSystem(42);

			LinkedHashSet<AVar> l = new LinkedHashSet<>();
			l.add(new AVar("size", Double.class, 123.45, "cm"));
			l.add(new AVar("weight", Double.class, 67.89, "kg"));

			Calendar c = Calendar.getInstance();
			c.set(1994, 11, 10);
			l.add(new AVar("birth", Calendar.class, c));
			ms.put("Static data", l);

			json = gson.toJson(ms); // TODO This is for a test only
			System.out.println(json);
		}

		{
			MonitoredSystem ms2 = gson.fromJson(json, MonitoredSystem.class);
			System.out.println(ms2);

			for (String k : ms2.keySet()) {
				System.out.println("-- "+k+" --");
				for (AVar av : ms2.get(k))
					System.out.println("\t"+av.getKey()+", "+(av.getValueType().cast(av.getValue())));
			}
		}

	}

	private LinkedHashSet<AVar> get(String birth) {
		return lhm.get(birth);
	}
	private Set<String> keySet() {
		return lhm.keySet();
	}

	@Override
	public String toString() {
		return "MonitoredSystem:{"+this.id+","+this.lhm+"}";
	}

	public LinkedHashMap<String, LinkedHashSet<AVar>> getLHM() {
		return lhm;
	}
}
