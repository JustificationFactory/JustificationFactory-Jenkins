package fr.axonic.avek.engine.instance.evidence;

import fr.axonic.base.ADate;
import fr.axonic.base.AEnum;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.engine.AStructure;
import fr.axonic.base.engine.AVarHelper;
import fr.axonic.validation.exception.VerificationException;

import java.util.Arrays;
import java.util.GregorianCalendar;

/**
 * Created by cduffau on 02/08/16.
 */
public class PathologySubjectInformations extends AStructure{

    private ARangedEnum<ObesityType> obesityType;
    private ADate beginningOfObesity;

    public PathologySubjectInformations() throws VerificationException {
        super();
        this.setLabel("Pathology Informations");
        this.setPath("fr.axonic.subject");
        this.setCode("pathology");

        obesityType=new ARangedEnum<>(ObesityType.class);
        obesityType.setLabel("Obesity Type");
        obesityType.setCode("obesityType");
        obesityType.setPath("fr.axonic.subject.pathology");
        obesityType.setRange(AVarHelper.transformToAVar(Arrays.asList(ObesityType.values())));

        beginningOfObesity=new ADate();
        beginningOfObesity.setCode("beginningObesity");
        beginningOfObesity.setPath("fr.axonic.subject.pathology");
        beginningOfObesity.setLabel("Beginning of obesity");
        super.init();
    }

    public AEnum<ObesityType> getObesityType() {
        return obesityType;
    }

    private void setObesityType(ARangedEnum<ObesityType> obesityType) {
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
