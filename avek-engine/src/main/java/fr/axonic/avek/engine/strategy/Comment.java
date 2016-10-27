package fr.axonic.avek.engine.strategy;

import fr.axonic.base.AString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cduffau on 04/08/16.
 */
@XmlRootElement
public class Comment {

    private String comments;

    @XmlElement
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
