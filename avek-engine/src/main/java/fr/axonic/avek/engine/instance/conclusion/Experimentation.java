package fr.axonic.avek.engine.instance.conclusion;

import fr.axonic.avek.engine.evidence.Element;
import fr.axonic.avek.engine.instance.evidence.Stimulation;
import fr.axonic.avek.engine.instance.evidence.Subject;


public class Experimentation extends Element {

    private Stimulation stimulation;
    private Subject subject;

    public Experimentation(Stimulation stimulation, Subject subject) {
        this.stimulation = stimulation;
        this.subject = subject;
    }

    public Stimulation getStimulation() {
        return stimulation;
    }

    public void setStimulation(Stimulation stimulation) {
        this.stimulation = stimulation;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Experimentation{" +
                "stimulation=" + stimulation +
                ", subject=" + subject +
                '}';
    }
}
