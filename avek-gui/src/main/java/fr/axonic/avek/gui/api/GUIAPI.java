package fr.axonic.avek.gui.api;

import java.util.Map;

/**
 * Created by cduffau on 12/08/16.
 */
public interface GUIAPI {

    void show(ViewType viewType, Map<ComponentType, Object> content) throws GUIException;
}
