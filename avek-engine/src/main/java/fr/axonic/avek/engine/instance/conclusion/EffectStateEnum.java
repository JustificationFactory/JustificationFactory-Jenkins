package fr.axonic.avek.engine.instance.conclusion;

import fr.axonic.base.engine.AEnumItem;

/**
 * Created by cduffau on 09/08/16.
 */
public enum EffectStateEnum implements AEnumItem{
    VERY_STRONG, STRONG, MEDIUM, LOW, VERY_LOW, UNKNOWN;

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
}
