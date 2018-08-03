package fr.axonic.avek.instance.clinical.conclusion;

import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.conclusion.Restriction;
import fr.axonic.avek.instance.clinical.evidence.Stimulation;
import fr.axonic.avek.instance.clinical.evidence.Subject;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Arrays;

/**
 * Created by cduffau on 22/06/16.
 */
@XmlRootElement
@XmlSeeAlso({Subject.class, Stimulation.class, Experimentation.class})
public class ExperimentationConclusion extends Conclusion<Experimentation> {

    private Subject subject;
    private Stimulation stimulation;
    public ExperimentationConclusion() {
        super();
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
        restrictions = Arrays.asList(new Restriction[]{subject,stimulation});
    }

    public Stimulation getStimulation() {
        return stimulation;
    }

    public void setStimulation(Stimulation stimulation) {
        if(this.stimulation!=null){
            restrictions.remove(stimulation);
        }
        this.stimulation = stimulation;
        restrictions.add(stimulation);

    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        if(this.subject!=null){
            restrictions.remove(subject);
        }
        this.subject = subject;
        restrictions.add(subject);
    }
}
