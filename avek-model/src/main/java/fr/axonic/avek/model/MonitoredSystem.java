package fr.axonic.avek.model;

import fr.axonic.base.AString;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AStructure;
import fr.axonic.base.engine.AVar;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Nathaël N on 29/06/16.
 */
public class MonitoredSystem extends AStructure {
	private final AString id;

	private final Set<AList<AEntity>> categories;

	public MonitoredSystem(AString id) {
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

	@Override
	public int hashCode() {
	    int hash = 17*id.hashCode();
        hash = hash*13 + categories.hashCode();
		return hash;
	}
}
