package fr.axonic.avek.model.base;



import fr.axonic.avek.model.verification.Verifiable;
import fr.axonic.avek.model.verification.Verify;
import fr.axonic.avek.model.verification.exception.VerificationException;

import javax.xml.bind.annotation.XmlTransient;

public class ANumber extends AVar<Number> implements Verifiable {


    public ANumber() {
        this(null);
    }

    public ANumber(Number value) {
        super(new Format(FormatType.NUMBER), value);
    }
    public ANumber(String label, Number value) {
        super(label,new Format(FormatType.NUMBER), value);
    }

    @Override
    @Verify
    public void verify(boolean verifyConsistency) throws VerificationException {
    }

    @Override
    public String toString() {
        return "ANumber{" +
                "value=" + getValue() + '\'' +
                ", code='" + getCode() + '\'' +
                ", path='" + getPath() + '\'' +
                '}';
    }
}
