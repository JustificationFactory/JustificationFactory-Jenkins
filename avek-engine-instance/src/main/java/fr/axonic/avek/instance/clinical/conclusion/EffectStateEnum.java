package fr.axonic.avek.instance.clinical.conclusion;

import fr.axonic.base.engine.AEnumItem;

/**
 * Created by cduffau on 09/08/16.
 */
public enum EffectStateEnum implements AEnumItem{
    VERY_STRONG("veryStrong","Very Strong"), STRONG("strong", "Strong"), MEDIUM("medium","Medium"), LOW("low","Low"), VERY_LOW("veryLow","Very Low"), UNKNOWN("unknown","Unknown");

    private String code, path, label;

    EffectStateEnum(String code, String label) {
        this.code = code;
        this.path = "fr.axonic.effectType.state";
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

    @Override
    public int getIndex() {
        return ordinal();
    }
}
