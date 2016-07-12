package fr.axonic.avek.model.base.engine;

public class Format {

    private FormatType formatType;

    public Format(FormatType formatType){
        this.formatType=formatType;
    }

    public FormatType getType() {
        return formatType;
    }

}
