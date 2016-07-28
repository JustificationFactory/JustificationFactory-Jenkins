package fr.axonic.avek.gui.model.structure;

import fr.axonic.avek.model.base.ARangedEnum;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nathaël N on 12/07/16.
 */
public class ExperimentResultsMap extends LinkedHashMap<String, ARangedEnum> {

	public List<ExperimentResult> getList() {
		return keySet().stream()
				.map(s -> new ExperimentResult(s, get(s)))
				.collect(Collectors.toList());
	}
}
