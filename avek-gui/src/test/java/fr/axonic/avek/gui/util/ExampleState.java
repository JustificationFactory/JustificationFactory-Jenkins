package fr.axonic.avek.gui.util;

import fr.axonic.base.engine.AEnumItem;

/**
 * Created by Nathaël N on 07/07/16.
 */
public enum ExampleState implements AEnumItem{
    VERY_LOW,
    LOW,
    MEDIUM,
    HIGH,
    VERY_HIGH;

    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public int getIndex() {
        return ordinal();
    }
}
