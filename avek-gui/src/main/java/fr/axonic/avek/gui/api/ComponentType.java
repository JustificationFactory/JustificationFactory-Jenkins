package fr.axonic.avek.gui.api;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cduffau on 12/08/16.
 */
public enum ComponentType {
    MONITORED_SYSTEM, EFFECTS, EXPERIMENTATION_PARAMETERS, COMMENTS, COMPLEMENTARY_FILES, SELECTION;

    private List<Class> compatibleObjects;

    ComponentType(Class ... compatibleObjects) {
        this.compatibleObjects = Arrays.asList(compatibleObjects);
    }

    public List<Class> getCompatibleObjects() {
        return compatibleObjects;
    }

    // TODO : handle inheritance
    public boolean isCompatibleObject(Object o){
        return compatibleObjects.contains(o.getClass());
    }

}
