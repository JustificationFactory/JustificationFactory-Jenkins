package fr.axonic.avek.instance.clinical.evidence;

import fr.axonic.base.AContiniousDate;
import fr.axonic.base.ADate;
import fr.axonic.base.engine.AStructure;
import fr.axonic.validation.exception.VerificationException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.GregorianCalendar;

/**
 * Created by cduffau on 09/08/16.
 */
@XmlRootElement
public class StimulationScheduler extends AStructure {

    private AContiniousDate from, to;

    public StimulationScheduler() {
        super();
        this.setLabel("Stimulation Scheduler");
        this.setCode("scheduler");
        this.setPath("fr.axonic.stimulation");
        from=new AContiniousDate();
        from.setLabel("From");
        from.setPath("fr.axonic.stimulation.scheduler");
        from.setCode("from");
        to=new AContiniousDate();
        to.setLabel("To");
        to.setPath("fr.axonic.stimulation.scheduler");
        to.setCode("to");
        super.init();
    }

    @XmlElement
    public ADate getFrom() {
        return from;
    }

    private void setFrom(AContiniousDate from) {
        this.from = from;
    }

    public void setFromValue(GregorianCalendar from) throws VerificationException {
        this.from.setValue(from);
    }

    @XmlElement
    public ADate getTo() {
        return to;
    }

    private void setTo(AContiniousDate to) {
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
