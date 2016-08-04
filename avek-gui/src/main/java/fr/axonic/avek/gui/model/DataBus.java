package fr.axonic.avek.gui.model;

import com.google.gson.internal.LinkedTreeMap;
import fr.axonic.avek.gui.model.json.Jsonifier;
import fr.axonic.avek.gui.util.Util;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.base.ADate;
import fr.axonic.base.ANumber;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nathaël N on 28/07/16.
 */
public class DataBus {

    public static MonitoredSystem getMonitoredSystem() {
        //String monitoredSystemJson = Util.getFileContent("json/subjectFile.json");
        //return new Jsonifier<>(MonitoredSystem.class).fromJson(monitoredSystemJson);

        MonitoredSystem ms = new MonitoredSystem(42);

        AList<AEntity> staticList = new AList<>(
                new ANumber("Id", 42),
                new ANumber("Age", 25),
                new ANumber("Sex", 1));
        staticList.setLabel("Static");
        ms.addCategory(staticList);

        AList<AEntity> pathologicList = new AList<>(
                new AString("Pathology", "OVERWEIGHT"),
                new ADate("Beginning", new GregorianCalendar()),
                new AString("Type", "Gynoid"));
        pathologicList.setLabel("Pathologic");
        ms.addCategory(pathologicList);

        AList<AEntity> dynamicList = new AList<>(
                new ANumber("Size", 123.45),
                new ANumber("Weight", 67),
                new ANumber("IMC", 67d / (1.2345 * 1.2345)));
        dynamicList.setLabel("Dynamic");
        ms.addCategory(dynamicList);

        return ms;
    }

    public static AList<AEntity> getExperimentParameters() {
        String experimentParametersJson = Util.getFileContent("json/parametersFile.json");

        return (AList<AEntity>) Jsonifier.toAEntity(experimentParametersJson);
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
