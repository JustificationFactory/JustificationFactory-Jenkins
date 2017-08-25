package fr.axonic.avek.instance.avek.evidence;

import fr.axonic.avek.engine.support.evidence.Evidence;

/**
 * Created by cduffau on 16/03/17.
 */
public class StimulationEvidence extends Evidence<Stimulation>{

    public StimulationEvidence(String name, Stimulation stimulation) {
        super(name,stimulation);
    }

    public StimulationEvidence() {
        super();
    }
}
