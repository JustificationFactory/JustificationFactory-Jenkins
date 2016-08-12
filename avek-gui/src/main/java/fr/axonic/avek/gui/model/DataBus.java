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
    private static final DataBus INSTANCE = new DataBus();

    private MonitoredSystem monitoredSystem;
    private AList<AEntity> experimentParams;
    private Map<String, ARangedEnum> experimentResults;

    public static MonitoredSystem getMonitoredSystem() {
        return INSTANCE.monitoredSystem;
    }
    public static void setMonitoredSystem(MonitoredSystem monitoredSystem) {
        INSTANCE.monitoredSystem = monitoredSystem;
    }

    public static AList<AEntity> getExperimentParams() {
        return INSTANCE.experimentParams;
    }
    public static void setExperimentParams(AList<AEntity> experimentParams) {
        INSTANCE.experimentParams = experimentParams;
    }

    public static Map<String, ARangedEnum> getExperimentResults() {
        return INSTANCE.experimentResults;
    }
    public static void setExperimentResults(Map<String, ARangedEnum> experimentResults) {
        INSTANCE.experimentResults = experimentResults;
    }

    // MOCK
    static {
        //String monitoredSystemJson = Util.getFileContent("json/MonitoredSystemFile.json");
        //return new Jsonifier<>(MonitoredSystem.class).fromJson(monitoredSystemJson);

        MonitoredSystem ms = new MonitoredSystem(new AString("id", "42X"));

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

        setMonitoredSystem(ms);
    }

    /**static {
        String experimentParametersJson = Util.getFileContent("json/parametersFile.json");
        setExperimentParams((AList<AEntity>)Jsonifier.toAEntity(experimentParametersJson));
    }

    static {
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

        setExperimentResults(expResMap);
    }*/
}
