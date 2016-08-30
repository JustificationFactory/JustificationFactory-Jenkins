package fr.axonic.avek.bus;

import fr.axonic.avek.engine.instance.conclusion.Effect;
import fr.axonic.avek.engine.instance.conclusion.EffectEnum;
import fr.axonic.avek.engine.instance.conclusion.EffectStateEnum;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.base.AEnum;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AVar;
import fr.axonic.validation.exception.VerificationException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Nathaël N on 28/07/16.
 */
public class DataBusOld {
    private final static Logger LOGGER = Logger.getLogger(DataBusOld.class);
    private static final DataBusOld INSTANCE = new DataBusOld();

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
        Map<EffectEnum, ARangedEnum> map = new LinkedHashMap<>();
        for(Effect e : experimentResults) {
            map.put(e.getEffectType().getValue(), e.getEffectType().getValue().getState());
        }
        setExperimentResults(map);
    }
    public static void setExperimentResults(Map<EffectEnum, ARangedEnum> experimentResults) {
        INSTANCE.experimentResults = new ArrayList<>();

        // Convert experiment results
        for (Map.Entry<EffectEnum, ARangedEnum> entry : experimentResults.entrySet()) {
            @SuppressWarnings({"unchecked", "Convert2Diamond"})
            List<Object> ls = new ArrayList<AEnum>(entry.getValue().getRange())
                    .stream()
                    .map(AVar::getValue)
                    .collect(Collectors.toList());
            JellyBeanItem jb = new JellyBeanItem<>(entry.getKey(), ls);
            INSTANCE.experimentResults.add(jb);

            Object value = entry.getValue().getValue();
            if(value != null) {
                LOGGER.debug("ARangedEnum value= " + value);
                //noinspection unchecked
                jb.setState(entry.getValue().getValue());
            }
        }

        LOGGER.debug("Experiment results set to: "+experimentResults);
    }
}
