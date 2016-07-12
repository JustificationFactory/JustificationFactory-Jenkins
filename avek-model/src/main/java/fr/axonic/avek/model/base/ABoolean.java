package fr.axonic.avek.model.base;

public class ABoolean extends AVar<Boolean> {

    public ABoolean() {
        this(null);
    }

    public ABoolean(String label, Boolean value) {
        super(label, new Format(FormatType.BOOLEAN),value);
    }

    public ABoolean(Boolean value) {
        this("",value);
    }

    /**public boolean booleanValue(){
        Boolean bValue= (Boolean) this.getValue();
        if(bValue==null){
            return false;
        }
        return bValue;
    }*/

}
