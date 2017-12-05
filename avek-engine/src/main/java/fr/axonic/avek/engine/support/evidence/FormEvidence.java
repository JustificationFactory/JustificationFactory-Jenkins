package fr.axonic.avek.engine.support.evidence;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FormEvidence extends Evidence<Form>{
    public FormEvidence(String name, Form element) {
        super(name, element);
    }

    public FormEvidence() {
    }
}
