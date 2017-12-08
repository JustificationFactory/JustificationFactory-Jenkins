package fr.axonic.avek.engine.support.instance;

import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Document;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocumentConclusion extends Conclusion<Document>{
    public DocumentConclusion() {
    }

    public DocumentConclusion(Document element) {
        super(element);
    }

    public DocumentConclusion(String name, Document element) {
        super(name, element);
    }
}
