package fr.axonic.avek.model.base;

import fr.axonic.avek.model.verification.Verify;
import fr.axonic.avek.model.verification.exception.VerificationException;

import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

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
}
