package fr.axonic.base.engine;

public enum FormatType implements AEnumItem{
    UNKNOWN,
    STRING,
    NUMBER,
    RANGED_NUMBER,
    RANGED_STRING,
    RANGED_ENUM,
    DATE,
    BOOLEAN,
    ENUM;

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
