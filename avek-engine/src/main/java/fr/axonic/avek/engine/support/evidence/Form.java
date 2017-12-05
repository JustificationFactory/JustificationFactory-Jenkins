package fr.axonic.avek.engine.support.evidence;

import fr.axonic.base.AString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Form extends Element{

    private List<AString> form;

    @XmlElement
    @XmlElementWrapper
    public List<AString> getForm() {
        return form;
    }

    public void setForm(List<AString> form) {
        this.form = form;
    }
}
