package fr.axonic.avek.engine.instance.strategy;

import fr.axonic.avek.engine.strategy.Project;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cduffau on 26/10/16.
 */
@XmlRootElement
public class AXONICProject implements Project{

    private Stimulator stimulator;
    private Pathology pathology;

    private AXONICProject() {
    }

    public AXONICProject(Stimulator stimulator, Pathology pathology) {
        this.stimulator = stimulator;
        this.pathology = pathology;
    }

    @XmlElement
    public Stimulator getStimulator() {
        return stimulator;
    }

    @XmlElement
    public Pathology getPathology() {
        return pathology;
    }

    private void setStimulator(Stimulator stimulator) {
        this.stimulator = stimulator;
    }

    private void setPathology(Pathology pathology) {
        this.pathology = pathology;
    }
}
