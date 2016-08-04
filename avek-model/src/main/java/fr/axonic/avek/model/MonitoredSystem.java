package fr.axonic.avek.model;

import fr.axonic.base.engine.*;

import java.util.*;

/**
 * Created by Nathaël N on 29/06/16.
 */
public class MonitoredSystem {
	private final int id;

	private final Set<AList<AEntity>> categories;

	public MonitoredSystem(int id) {
		this.id = id;
        this.categories = new LinkedHashSet<>();
	}

	public void addCategory(AList<AEntity> structure) {
	    categories.add(structure);
    }

	public Set<AList<AEntity>> getCategories() {
	    return categories;
    }

	@Override
	public String toString() {
		return "MonitoredSystem="+this.id+", "+this.categories +"}";
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MonitoredSystem)
			return categories.equals(((MonitoredSystem)obj).categories);
		return super.equals(obj);
	}
}
