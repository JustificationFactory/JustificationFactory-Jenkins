package fr.axonic.base;


import fr.axonic.base.engine.AVar;
import fr.axonic.base.format.EnumFormat;
import fr.axonic.validation.Verifiable;
import fr.axonic.validation.Verify;
import fr.axonic.validation.exception.VerificationException;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cduffau on 08/07/16.
 */
@XmlRootElement
public class AEnum<T extends Enum<T>> extends AVar<T> implements Verifiable {


    public AEnum(){
        this(null);
    }
    public AEnum(T value) {
        super(new EnumFormat(), value);
    }


    @Override
    @Verify
    public void verify(boolean verifyConsistency) throws VerificationException {
        // DO NOTHING
        // There are no constraints to be verified in AVar.
    }
    @Override
    public String toString() {
        return "AEnum{"+super.toString()+"}";
    }
}
