package fr.axonic;

import fr.axonic.base.engine.AEnumItem;

/**
 * Created by cduffau on 29/07/16.
 */
public enum TestEnum implements AEnumItem{
    A,B,C,D;
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
