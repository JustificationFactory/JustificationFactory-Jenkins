package fr.axonic;

import fr.axonic.base.engine.AEnumItem;

/**
 * Created by cduffau on 29/07/16.
 */
public enum TestEnum implements AEnumItem{
    A("a","A test"),B("b","B test"),C("c","C test"),D("d","D test");

    private String code, path, label;

    TestEnum(String code, String label) {
        this.code = code;
        this.path = "fr.test";
        this.label = label;
    }

    private void setCode(String code) {
        this.code = code;
    }

    private void setPath(String path) {
        this.path = path;
    }

    private void setLabel(String label) {
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



}
