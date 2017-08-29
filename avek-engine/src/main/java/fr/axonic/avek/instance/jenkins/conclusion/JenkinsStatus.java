package fr.axonic.avek.instance.jenkins.conclusion;

import fr.axonic.avek.engine.support.evidence.Element;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cduffau on 24/08/17.
 */
@XmlRootElement
public class JenkinsStatus extends Element{

    private JenkinsStatusEnum status;


    public JenkinsStatus(JenkinsStatusEnum status, String version) {
        super(version);
        this.status = status;
    }
    public JenkinsStatus(JenkinsStatusEnum status) {
        this(status,null);
    }

    public JenkinsStatus() {
        super();
    }

    @XmlElement
    public JenkinsStatusEnum getStatus() {
        return status;
    }

    public void setStatus(JenkinsStatusEnum status) {
        this.status = status;
    }


}
