package fr.axonic.avek.instance.redmine;

import fr.axonic.avek.engine.support.evidence.Document;
import fr.axonic.avek.engine.support.instance.DocumentEvidence;

public class RedmineDocumentApproval extends RedmineEvidence {

    public RedmineDocumentApproval(String name, Document element) {
        super(name, element);
    }

    public RedmineDocumentApproval() {
        super();
    }
}
