package fr.axonic.avek.gui.model;

import fr.axonic.base.engine.AEnumItem;

/**
 * Created by Nathaël N on 07/07/16.
 */
public enum BooleanState implements AEnumItem{
    TRUE, FALSE;

    @Override
    public String getLabel() {
        return name().toLowerCase();
    }

    @Override
    public String getCode() {
        return "";
    }

    @Override
    public String getPath() {
        return "";
    }

    @Override
    public int getIndex(){
        return ordinal();
    }
}
