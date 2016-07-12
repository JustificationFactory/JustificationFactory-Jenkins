package fr.axonic.avek.gui.model.structure;

import fr.axonic.avek.model.base.ARangedEnum;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nathaël N on 12/07/16.
 */
public class ExperimentationResults {
	private final Map<String, ARangedEnum> results;

	public ExperimentationResults() {
		results = new HashMap<>();
	}
	public void put(String ae1, ARangedEnum aEnum) {
		results.put(ae1, aEnum);
	}

	public Collection getList() {
		return results.values();
	}
}
