package fr.axonic.avek.model.base;

public class AString extends AVar {
    public AString() {
        this(null);
    }

    public AString(Object value) {
        super(new Format(FormatType.STRING),value);
    }
    public AString(String label, Object value) {
        super(label,new Format(FormatType.STRING), value);
    }
}
