package fr.axonic.avek.gui.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by cduffau on 12/08/16.
 */
public abstract class GUIAPI extends Observable {
    public abstract void show(ViewType viewType, Map<ComponentType, Object> content) throws GUIException;
}
