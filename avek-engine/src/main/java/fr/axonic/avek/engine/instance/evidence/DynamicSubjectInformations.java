package fr.axonic.avek.engine.instance.evidence;

import fr.axonic.base.ANumber;
import fr.axonic.base.engine.AStructure;
import fr.axonic.validation.exception.VerificationException;

/**
 * Created by cduffau on 02/08/16.
 */
public class DynamicSubjectInformations extends AStructure{

    private ANumber weight;
    private ANumber bmi;

    public DynamicSubjectInformations() throws VerificationException {
        super();
        this.setLabel("Dynamic Informations");
        this.setCode("dynamic");
        this.setPath("fr.axonic.subject");

        this.weight=new ANumber();
        this.bmi=new ANumber();

        weight.setPath("fr.axonic.subject.dynamic");
        bmi.setPath("fr.axonic.subject.dynamic");
        weight.setCode("weight");
        bmi.setCode("bmi");

        weight.setUnit("kg");
        bmi.setUnit("kg / m²");

        weight.setLabel("Weight");
        bmi.setLabel("Body Mass Index (BMI)");

        init();
    }

    public ANumber getWeight() {
        return weight;
    }

    private void setWeight(ANumber weight) {
        this.weight = weight;
    }

    public void setWeightValue(double weight) throws VerificationException {
        this.weight.setValue(weight);
    }

    public ANumber getBmi() {
        return bmi;
    }

    private void setBmi(ANumber bmi) {
        this.bmi = bmi;
    }

    public void setBmiValue(double bmi) throws VerificationException {
        this.bmi.setValue(bmi);
    }

    @Override
    public String toString() {
        return "DynamicSubjectInformations{" +
                "weight=" + weight +
                ", bmi=" + bmi +
                '}';
    }
}
