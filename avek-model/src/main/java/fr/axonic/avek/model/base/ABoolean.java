package fr.axonic.avek.model.base;

public class ABoolean extends AVar {

    public ABoolean() {
        this(null);
    }

    public ABoolean(String label, boolean value) {
        super(label, new Format(FormatType.BOOLEAN),value);
    }
    public ABoolean(Object value) {
        super(new Format(FormatType.BOOLEAN),value);
    }

    public boolean booleanValue(){
        Boolean bValue= (Boolean) this.getValue();
        if(bValue==null){
            return false;
        }
        return bValue;
    }

}
