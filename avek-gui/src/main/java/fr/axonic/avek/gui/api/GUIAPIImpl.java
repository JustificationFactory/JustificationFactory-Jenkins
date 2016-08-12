package fr.axonic.avek.gui.api;

import java.util.Map;

/**
 * Created by cduffau on 12/08/16.
 */
public class GUIAPIImpl implements GUIAPI{


    @Override
    public void show(ViewType viewType, Map<ComponentType, Object> content) throws GUIException {
        if(!viewType.isContentCompatible(content)){
            throw new GUIException("Wrong content for "+viewType);
        }
        // TODO : le reste
    }
}
