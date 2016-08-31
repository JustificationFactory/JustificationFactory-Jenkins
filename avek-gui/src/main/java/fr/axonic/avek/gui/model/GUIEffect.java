package fr.axonic.avek.gui.model;

import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by nathael on 31/08/16.
 */
public class GUIEffect extends ArrayList<JellyBeanItem> {

    public GUIEffect() {
        super();
    }
    public GUIEffect(Collection<JellyBeanItem> selected) {
        super(selected);
    }
}
