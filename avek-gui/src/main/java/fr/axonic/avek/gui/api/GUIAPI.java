package fr.axonic.avek.gui.api;

import java.util.Map;
import java.util.Observable;

/**
 * Created by cduffau on 12/08/16.
 */
public abstract class GUIAPI extends Observable {
    public abstract void show(String name, ViewType viewType, Map<ComponentType, Object> content) throws GUIException;
}
