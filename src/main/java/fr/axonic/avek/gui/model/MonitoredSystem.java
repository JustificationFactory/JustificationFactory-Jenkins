package fr.axonic.avek.gui.model;

import fr.axonic.avek.gui.model.base.AVar;

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

	@Override
	public String toString() {
		return "MonitoredSystem= "+this.id+", "+this.categories +"}";
	}

	public void addCategory(String s) {
		categories.put(s, new LinkedHashSet<>());
	}

	public void addAVar(String s, AVar aVar) {
		categories.get(s).add(aVar);
	}

	public Map<String, Set<AVar>> getMap() {
		return categories;
	}
}
