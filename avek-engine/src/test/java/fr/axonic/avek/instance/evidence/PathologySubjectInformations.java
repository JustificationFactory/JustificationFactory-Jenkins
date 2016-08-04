package fr.axonic.avek.instance.evidence;

import fr.axonic.base.ADate;
import fr.axonic.base.AEnum;
import fr.axonic.base.ANumber;
import fr.axonic.base.engine.AStructure;
import fr.axonic.validation.exception.VerificationException;

import java.util.GregorianCalendar;

/**
 * Created by cduffau on 02/08/16.
 */
public class PathologySubjectInformations extends AStructure{

    private AEnum<ObesityType> obesityType;
    private ADate beginningOfObesity;

    public PathologySubjectInformations() {
        super();
        this.setLabel("Pathology Subject Informations");
        this.setPath("fr.axonic.subject");
        this.setCode("pathology");

        obesityType=new AEnum<>();
        obesityType.setLabel("Obesity Type");
        obesityType.setCode("obesityType");
        obesityType.setPath("fr.axonic.subject.pathology");

        beginningOfObesity=new ADate();
        beginningOfObesity.setCode("beginningObesity");
        beginningOfObesity.setPath("fr.axonic.subject.pathology");
        beginningOfObesity.setLabel("Beginning of obesity");
        super.init();
    }

    public AEnum<ObesityType> getObesityType() {
        return obesityType;
    }

    private void setObesityType(AEnum<ObesityType> obesityType) {
        this.obesityType = obesityType;
    }

    public void setObesityTypeValue(ObesityType obesityType) throws VerificationException {
        this.obesityType.setValue(obesityType);
    }

    public ADate getBeginningOfObesity() {
        return beginningOfObesity;
    }

    private void setBeginningOfObesity(ADate beginningOfObesity) {
        this.beginningOfObesity = beginningOfObesity;
    }

    public void setBeginningOfObesityValue(GregorianCalendar beginningOfObesity) throws VerificationException {
        this.beginningOfObesity.setValue(beginningOfObesity);
    }

    @Override
    public String toString() {
        return "PathologySubjectInformations{" +
                "obesityType=" + obesityType +
                ", beginningOfObesity=" + beginningOfObesity +
                '}';
    }
}
