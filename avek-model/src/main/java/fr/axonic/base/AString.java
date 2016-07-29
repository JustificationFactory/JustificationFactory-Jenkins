package fr.axonic.base;


import fr.axonic.base.engine.AVar;
import fr.axonic.base.engine.Format;
import fr.axonic.base.engine.FormatType;
import fr.axonic.validation.Verify;
import fr.axonic.validation.exception.VerificationException;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AString extends AVar<String> {

    public AString() {
        this(null);
    }

    public AString(String value) {
        super(new Format(FormatType.STRING),value);
    }
    public AString(String label, String value) {
        super(label,new Format(FormatType.STRING), value);
    }

    @Override
    @Verify
    public void verify(boolean verifyConsistency) throws VerificationException {
        // DO NOTHING
        // There are no constraints to be verified in AVar.
    }
    @Override
    public String toString() {
        return "AString{"+super.toString()+"}";
    }
}
