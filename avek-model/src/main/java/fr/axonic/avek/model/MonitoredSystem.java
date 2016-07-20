package fr.axonic.avek.model;

import com.google.gson.Gson;
import fr.axonic.avek.model.base.engine.AVar;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Nathaël N on 29/06/16.
 */
public class MonitoredSystem {
	private final int id;
	private final Map<String, Set<AVar>> categories;

	public MonitoredSystem(int id) {
		this.id = id;
		this.categories = new LinkedHashMap<>();
	}

	public void addCategory(String s) {
		categories.put(s, new LinkedHashSet<>());
	}

	public void addAVar(String s, AVar aVar) {
		if(!categories.containsKey(s))
			throw new NullPointerException("Category "+s+" not found");

		categories.get(s).add(aVar);
	}

	public Map<String, Set<AVar>> getMap() {
		return categories;
	}

	public String toJson() {
		return new Gson().toJson(this);
	}
	public static MonitoredSystem fromJson(String json) {
		return new Gson().fromJson(json, MonitoredSystem.class);
	}


	@Override
	public String toString() {
		return "MonitoredSystem= "+this.id+", "+this.categories +"}";
	}


	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MonitoredSystem)
			return categories.equals(((MonitoredSystem)obj).categories);
		return super.equals(obj);
	}
}
