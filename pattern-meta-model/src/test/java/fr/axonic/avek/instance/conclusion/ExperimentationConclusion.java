package fr.axonic.avek.instance.conclusion;

import fr.axonic.avek.meta.conclusion.Conclusion;
import fr.axonic.avek.meta.conclusion.Limit;
import fr.axonic.avek.instance.evidence.Stimulation;
import fr.axonic.avek.instance.evidence.Subject;

import java.util.Arrays;

/**
 * Created by cduffau on 22/06/16.
 */
public class ExperimentationConclusion extends Conclusion<Experimentation> {

    private Subject subject;
    private Stimulation stimulation;
    public ExperimentationConclusion() {

    }
    public ExperimentationConclusion(Subject subject, Stimulation stimulation) {
        this.subject = subject;
        this.stimulation = stimulation;
        limits = Arrays.asList(new Limit[]{subject,stimulation});
    }

    public ExperimentationConclusion(String name, Experimentation element, Subject subject, Stimulation stimulation) {
        super(name, element);
        this.subject = subject;
        this.stimulation = stimulation;
    }
}
