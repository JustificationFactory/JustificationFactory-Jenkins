package fr.axonic.avek.instance.avek.evidence;

import fr.axonic.avek.engine.support.evidence.Evidence;

/**
 * Created by cduffau on 17/03/17.
 */
public class ResultsEvidence extends Evidence<Result>{
    public ResultsEvidence(String name, Result element) {
        super(name, element);
    }
}
