package fr.axonic.avek.instance.redmine;

import fr.axonic.avek.engine.kernel.Artifact;
import fr.axonic.avek.engine.support.evidence.Document;
import fr.axonic.avek.engine.support.instance.DocumentEvidence;

import java.util.ArrayList;
import java.util.List;

public class RedmineEvidence extends DocumentEvidence {


    public RedmineEvidence(String name, Document element) {
        super(name, element);
    }

    public RedmineEvidence() {
        super();
    }

}
