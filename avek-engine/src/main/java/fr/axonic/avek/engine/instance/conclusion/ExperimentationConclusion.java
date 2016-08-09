package fr.axonic.avek.engine.instance.conclusion;

import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.conclusion.Limit;
import fr.axonic.avek.engine.instance.evidence.Stimulation;
import fr.axonic.avek.engine.instance.evidence.Subject;

import java.util.Arrays;

/**
 * Created by cduffau on 22/06/16.
 */
public class ExperimentationConclusion extends Conclusion<Experimentation> {

    private Subject subject;
    private Stimulation stimulation;
    public ExperimentationConclusion() {

    }
    public ExperimentationConclusion(String name, Subject subject, Stimulation stimulation) {
        this(name,new Experimentation(stimulation, subject), subject, stimulation);
    }

    public ExperimentationConclusion(String name, Experimentation element) {
        this(name,element,element.getSubject(), element.getStimulation());
    }

    protected ExperimentationConclusion(String name, Experimentation experimentation, Subject subject, Stimulation stimulation) {
        super(name, experimentation);
        this.subject = subject;
        this.stimulation = stimulation;
        limits = Arrays.asList(new Limit[]{subject,stimulation});
    }

    public Stimulation getStimulation() {
        return stimulation;
    }

    public void setStimulation(Stimulation stimulation) {
        if(this.stimulation!=null){
            limits.remove(stimulation);
        }
        this.stimulation = stimulation;
        limits.add(stimulation);

    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        if(this.subject!=null){
            limits.remove(subject);
        }
        this.subject = subject;
        limits.add(subject);
    }
}
