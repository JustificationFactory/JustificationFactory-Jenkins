package fr.axonic.avek.gui.model.base;

public class Format {

    private FormatType formatType;

    public Format(FormatType formatType){
        this.formatType=formatType;
    }

    public FormatType getType() {
        return formatType;
    }

}
