package fr.axonic.avek.gui.model;

import fr.axonic.avek.engine.instance.conclusion.Effect;
import fr.axonic.avek.gui.Orchestrator;
import fr.axonic.avek.gui.components.jellybeans.JellyBean;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.base.*;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AVar;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Nathaël N on 28/07/16.
 */
public class DataBus {
    private final static Logger LOGGER = Logger.getLogger(DataBus.class);
    private static final DataBus INSTANCE = new DataBus();

    private MonitoredSystem monitoredSystem;
    private AList<AEntity> experimentParams;
    private List<JellyBeanItem> experimentResults;

    public static MonitoredSystem getMonitoredSystem() {
        return INSTANCE.monitoredSystem;
    }
    public static void setMonitoredSystem(MonitoredSystem monitoredSystem) {
        INSTANCE.monitoredSystem = monitoredSystem;
        LOGGER.debug("Monitored system set to: "+monitoredSystem);
    }

    public static AList<AEntity> getExperimentParams() {
        return INSTANCE.experimentParams;
    }
    public static void setExperimentParams(AList<AEntity> experimentParams) {
        INSTANCE.experimentParams = experimentParams;
        LOGGER.debug("Experiment parameters set to: "+experimentParams);
    }

    public static List<JellyBeanItem> getExperimentResults() {
        return INSTANCE.experimentResults;
    }

    public static void setExperimentResults(AList<Effect> experimentResults) {
        Map<String, ARangedEnum> map = new LinkedHashMap<>();
        for(Effect e : experimentResults) {
            map.put(e.getEffectType().getValue().toString(), e.getEffectType().getValue().getState());
        }
        setExperimentResults(map);
    }
    public static void setExperimentResults(Map<String, ARangedEnum> experimentResults) {
        INSTANCE.experimentResults = new ArrayList<>();

        // Convert experiment results
        for (Map.Entry<String, ARangedEnum> entry : experimentResults.entrySet()) {
            List<Object> ls = new ArrayList<AEnum>(entry.getValue().getRange())
                    .stream()
                    .map(AVar::getValue)
                    .collect(Collectors.toList());
            JellyBeanItem jb = new JellyBeanItem<>(entry.getKey(), ls);
            INSTANCE.experimentResults.add(jb);

            Object value = entry.getValue().getValue();
            if(value != null) {
                LOGGER.debug("ARangedEnum value= " + value);
               jb.setState(entry.getValue().getValue());
            }
        }

        LOGGER.debug("Experiment results set to: "+experimentResults);
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

    /*

    static {
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
    }

    */
}
