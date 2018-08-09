package fr.axonic.avek.instance.redmine;

import fr.axonic.avek.engine.support.evidence.Document;
import fr.axonic.avek.engine.support.instance.DocumentEvidence;

public class RedmineDocumentEvidence extends RedmineEvidence {

    public RedmineDocumentEvidence(String name, Document element) {
        super(name, element);
    }

    public RedmineDocumentEvidence() {
        super();
    }
}
