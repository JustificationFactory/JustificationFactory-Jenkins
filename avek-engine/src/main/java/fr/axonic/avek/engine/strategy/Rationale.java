package fr.axonic.avek.engine.strategy;

import fr.axonic.avek.instance.strategy.AXONICProject;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by cduffau on 26/10/16.
 */
@XmlRootElement
@XmlSeeAlso(AXONICProject.class)
public class Rationale<P extends Project> {

    private P project;

    public Rationale(P project) {
        this.project = project;
    }

    private Rationale() {
    }

    @XmlAnyElement
    public P getProject() {
        return project;
    }

    private void setProject(P project) {
        this.project = project;
    }
}
