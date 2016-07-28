package fr.axonic.avek.gui.model;

import fr.axonic.avek.gui.model.json.Jsonifier;
import fr.axonic.avek.gui.model.structure.ExperimentResultsMap;
import fr.axonic.avek.gui.util.Util;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.engine.AEntity;
import fr.axonic.avek.model.base.engine.AList;

/**
 * Created by Nathaël N on 28/07/16.
 */
public class DataBus {

	public static MonitoredSystem getMonitoredSystem() {
		String monitoredSystemJson = Util.getFileContent("files/subjectFile.json");
		return MonitoredSystem.fromJson(monitoredSystemJson);
	}

	public static AList<AEntity> getExperimentParameters() {
		String experimentParametersJson = Util.getFileContent("files/parametersFile.json");
		AList<AEntity> list = Jsonifier.toAListofAListAndAVar(experimentParametersJson);

		return list;
	}

	public static ExperimentResultsMap getExperimentResults() {
		String experimentResultsJson = Util.getFileContent("files/resultEnum1.json");

		ExperimentResultsMap expResMap =
				new Jsonifier<>(ExperimentResultsMap.class)
						.fromJson(experimentResultsJson);

		return expResMap;
	}
}
