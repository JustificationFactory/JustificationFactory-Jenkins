package fr.axonic.avek.engine.strategy;

import fr.axonic.avek.engine.support.evidence.Evidence;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cduffau on 26/10/16.
 */
@XmlRootElement
public class Actor extends Evidence{
    private String name;
    private Role role;

    private Actor() {
    }

    public Actor(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
