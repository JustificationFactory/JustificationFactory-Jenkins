package fr.axonic.avek.engine.support.instance;

import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Form;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FormConclusion extends Conclusion<Form> {
    public FormConclusion(String name, Form element) {
        super(name, element);
    }

    public FormConclusion() {
    }

    public FormConclusion(Form element) {
        super(element);
    }
}
