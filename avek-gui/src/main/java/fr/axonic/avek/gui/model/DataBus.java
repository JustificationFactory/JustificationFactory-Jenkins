package fr.axonic.avek.gui.model;

import com.google.gson.internal.LinkedTreeMap;
import fr.axonic.avek.gui.model.json.Jsonifier;
import fr.axonic.avek.gui.util.Util;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nathaël N on 28/07/16.
 */
public class DataBus {

    public static MonitoredSystem getMonitoredSystem() {
        String monitoredSystemJson = Util.getFileContent("json/subjectFile.json");
        return new Jsonifier<>(MonitoredSystem.class).fromJson(monitoredSystemJson);
    }

    public static AList<AEntity> getExperimentParameters() {
        String experimentParametersJson = Util.getFileContent("json/parametersFile.json");
        AList<AEntity> list = (AList<AEntity>) Jsonifier.toAEntity(experimentParametersJson);

        return list;
    }

    public static Map<String, ARangedEnum> getExperimentResults() {
        String experimentResultsJson = Util.getFileContent("json/resultEnum1.json");

        Map<String, LinkedTreeMap> map1 =
                new Jsonifier<>(HashMap.class)
                        .fromJson(experimentResultsJson);

        Map<String, ARangedEnum> expResMap = new HashMap<>();
        for (Map.Entry<String, LinkedTreeMap> entry : map1.entrySet()) {
            expResMap.put(entry.getKey(),
                    new Jsonifier<>(ARangedEnum.class)
                            .fromJson(Jsonifier.toJson(entry.getValue())));
        }

        System.out.println(expResMap);

        return expResMap;
    }
}
