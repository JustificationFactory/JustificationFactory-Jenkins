package fr.axonic.avek.model.base;

import fr.axonic.avek.model.base.engine.AVar;
import fr.axonic.avek.model.base.engine.Format;
import fr.axonic.avek.model.base.engine.FormatType;
import fr.axonic.avek.model.verification.Verifiable;
import fr.axonic.avek.model.verification.Verify;
import fr.axonic.avek.model.verification.exception.VerificationException;

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
        super(new Format(FormatType.ENUM), value);
    }


    @Override
    @Verify
    public void verify(boolean verifyConsistency) throws VerificationException {
        // DO NOTHING
        // There are no constraints to be verified in AVar.
    }
}
