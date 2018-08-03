package fr.axonic.avek.instance.clinical.strategy;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AXONICProject)) return false;

        AXONICProject that = (AXONICProject) o;

        if (stimulator != that.stimulator) return false;
        return pathology == that.pathology;
    }

    @Override
    public int hashCode() {
        int result = stimulator != null ? stimulator.hashCode() : 0;
        result = 31 * result + (pathology != null ? pathology.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AXONICProject{" +
                "stimulator=" + stimulator +
                ", pathology=" + pathology +
                '}';
    }

    @Override
    public String name() {
        return stimulator + "for "+pathology;
    }
}
