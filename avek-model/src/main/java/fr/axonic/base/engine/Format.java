package fr.axonic.base.engine;

public class Format {

    private FormatType formatType;

    public Format(FormatType formatType){
        this.formatType=formatType;
    }

    public FormatType getType() {
        return formatType;
    }

    @Override
    public String toString() {
        return "Format{" +
                "formatType=" + formatType +
                '}';
    }
}
