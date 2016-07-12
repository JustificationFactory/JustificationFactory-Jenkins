package fr.axonic.avek.model.base;

import fr.axonic.avek.model.base.engine.AVar;
import fr.axonic.avek.model.base.engine.Format;
import fr.axonic.avek.model.base.engine.FormatType;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
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
