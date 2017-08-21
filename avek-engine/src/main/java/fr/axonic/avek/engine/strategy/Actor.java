package fr.axonic.avek.engine.strategy;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import fr.axonic.avek.engine.support.evidence.Evidence;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cduffau on 26/10/16.
 */
@XmlRootElement
public class Actor extends Evidence{
    private String actorName;
    private Role role;

    private Actor() {
    }

    public Actor(String actorName, Role role) {
        this.name = name;
        this.role = role;
    }
    @XmlElement
    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }



    @XmlElement
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
