package fr.axonic.avek.gui.model;

import fr.axonic.avek.gui.model.json.Jsonifier;
import fr.axonic.avek.gui.model.structure.ExperimentResultsMap;
import fr.axonic.avek.gui.util.Util;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;

/**
 * Created by Nathaël N on 28/07/16.
 */
public class DataBus {

	public static MonitoredSystem getMonitoredSystem() {
		String monitoredSystemJson = Util.getFileContent("json/subjectFile.json");
		return MonitoredSystem.fromJson(monitoredSystemJson);
	}

	public static AList<AEntity> getExperimentParameters() {
		String experimentParametersJson = Util.getFileContent("json/parametersFile.json");
		AList<AEntity> list = (AList<AEntity>) Jsonifier.toAEntity(experimentParametersJson);

		return list;
	}

	public static ExperimentResultsMap getExperimentResults() {
		String experimentResultsJson = Util.getFileContent("json/resultEnum1.json");

		ExperimentResultsMap expResMap =
				new Jsonifier<>(ExperimentResultsMap.class)
						.fromJson(experimentResultsJson);

		return expResMap;
	}
}
