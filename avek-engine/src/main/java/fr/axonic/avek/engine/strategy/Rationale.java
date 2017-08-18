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

    @Override
    public String toString() {
        return "Rationale{" +
                "project=" + project +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rationale)) return false;

        Rationale<?> rationale = (Rationale<?>) o;

        return project != null ? project.equals(rationale.project) : rationale.project == null;
    }

    @Override
    public int hashCode() {
        return project != null ? project.hashCode() : 0;
    }
}
