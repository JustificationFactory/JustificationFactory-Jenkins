package fr.axonic.avek.instance.redmine;

import fr.axonic.avek.engine.support.Support;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class TransmittedSupports {

    private List<Support> supports;

    public TransmittedSupports() {
        // Empty for XML.
    }

    public TransmittedSupports(List<Support> supports) {
        this.supports = supports;
    }

    @XmlElement
    @XmlElementWrapper
    public List<Support> getSupports() {
        return supports;
    }

    public void setSupports(List<Support> supports) {
        this.supports = supports;
    }
}
