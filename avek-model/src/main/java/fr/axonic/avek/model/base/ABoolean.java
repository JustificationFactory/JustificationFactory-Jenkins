package fr.axonic.avek.model.base;

public class ABoolean extends AVar {

    public ABoolean() {
        this(null);
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
