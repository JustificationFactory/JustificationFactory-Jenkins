package fr.axonic.avek.engine;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by cduffau on 27/10/16.
 */
@XmlRootElement(name="root")
public class StepsWrapper {

    private List<Step> steps;

    public StepsWrapper() {
    }

    public StepsWrapper(List<Step> steps) {
        this.steps = steps;
    }

    @XmlElement(name="step")
    @XmlElementWrapper
    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
