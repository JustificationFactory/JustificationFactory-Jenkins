package fr.axonic.avek.gui.model.structure;

import fr.axonic.avek.model.base.ARangedEnum;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nathaël N on 12/07/16.
 */
public class ExperimentationResultsMap extends HashMap<String, ARangedEnum> {

	public List<ExperimentationResult> getList() {
		return keySet().stream()
				.map(s -> new ExperimentationResult(s, get(s)))
				.collect(Collectors.toList());
	}
}
