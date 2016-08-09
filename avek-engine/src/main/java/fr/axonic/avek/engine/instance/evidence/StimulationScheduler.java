package fr.axonic.avek.engine.instance.evidence;

import fr.axonic.base.ADate;
import fr.axonic.base.engine.AStructure;
import fr.axonic.validation.exception.VerificationException;

import java.util.GregorianCalendar;

/**
 * Created by cduffau on 09/08/16.
 */
public class StimulationScheduler extends AStructure {

    private ADate from, to;

    public StimulationScheduler() {
        super();
        this.setLabel("Stimulation Scheduler");
        this.setCode("scheduler");
        this.setPath("fr.axonic.stimulation");
        from=new ADate();
        from.setLabel("From");
        from.setPath("fr.axonic.stimulation.scheduler");
        from.setCode("from");
        to=new ADate();
        to.setLabel("To");
        to.setPath("fr.axonic.stimulation.scheduler");
        to.setCode("to");
        super.init();
    }

    public ADate getFrom() {
        return from;
    }

    private void setFrom(ADate from) {
        this.from = from;
    }

    public void setFromValue(GregorianCalendar from) throws VerificationException {
        this.from.setValue(from);
    }

    public ADate getTo() {
        return to;
    }

    private void setTo(ADate to) {
        this.to = to;
    }


    public void setToValue(GregorianCalendar to) throws VerificationException {
        this.to.setValue(to);
    }

    @Override
    public String toString() {
        return "StimulationPeriod{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
