package fr.axonic.avek.gui.api;

import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.base.engine.AList;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by cduffau on 12/08/16.
 */
public enum ComponentType {
    MONITORED_SYSTEM(MonitoredSystem.class), EFFECTS(List.class), EXPERIMENTATION_PARAMETERS(AList.class), COMMENTS(String.class), COMPLEMENTARY_FILES, SELECTION(List.class);

    private List<Class> compatibleObjects;

    ComponentType(Class ... compatibleObjects) {
        this.compatibleObjects = Arrays.asList(compatibleObjects);
    }

    public List<Class> getCompatibleObjects() {
        return compatibleObjects;
    }

    public boolean isCompatibleObject(Object o){
        // Handle inheritance
        // TODO review following
        for(Class c : compatibleObjects) {
            if(c.isInstance(o))
                return true;
        }
        return false;

        //return compatibleObjects.contains(o.getClass());
    }

}
