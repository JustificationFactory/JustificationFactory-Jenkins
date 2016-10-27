package fr.axonic.avek.gui.util;

import fr.axonic.base.engine.AEnumItem;

/**
 * Created by cduffau on 09/08/16.
 */
public enum TestEnum implements AEnumItem{
    A("a","A"), B("b", "B"), C("c","C"), D("d","D");

    private final String code;
    private final String path;
    private final String label;

    TestEnum(String code, String label) {
        this.code = code;
        this.path = "fr.axonic.avek.gui.util";
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public int getIndex() {
        return ordinal();
    }
}
