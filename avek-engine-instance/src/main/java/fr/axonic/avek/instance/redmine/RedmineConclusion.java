package fr.axonic.avek.instance.redmine;

import fr.axonic.avek.engine.support.evidence.Document;
import fr.axonic.avek.engine.support.instance.DocumentConclusion;

public class RedmineConclusion extends DocumentConclusion {

    public static RedmineConclusion create(RedmineDocumentEvidence evidence) {
        RedmineConclusion redmineConclusion = new RedmineConclusion();
        redmineConclusion.setName(evidence.getName() + " validated");

        Document element = new Document(evidence.getElement().getUrl());
        element.setVersion(null);
        redmineConclusion.setElement(element);

        return redmineConclusion;
    }
}
