package fr.axonic.avek.instance.redmine;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.instance.DocumentConclusion;

import java.util.List;

public class RedmineConclusion extends DocumentConclusion {


    public static RedmineConclusion create(RedmineDocumentEvidence evidence){
        RedmineConclusion redmineConclusion =new RedmineConclusion();
        redmineConclusion.setName(evidence.getName() + " validated");
        redmineConclusion.setElement(evidence.getElement());
        return redmineConclusion;
    }
}
