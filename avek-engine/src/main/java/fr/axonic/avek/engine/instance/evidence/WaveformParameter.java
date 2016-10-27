package fr.axonic.avek.engine.instance.evidence;

import fr.axonic.base.AContinuousNumber;
import fr.axonic.base.ANumber;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.engine.AStructure;
import fr.axonic.validation.exception.VerificationException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cduffau on 09/08/16.
 */
@XmlRootElement
public class WaveformParameter extends AStructure{
    private AContinuousNumber amplitude;
    private AContinuousNumber frequency;
    private AContinuousNumber duration;

    public WaveformParameter() throws VerificationException {
        super();
        this.amplitude=new AContinuousNumber();
        amplitude.setLabel("Amplitude");
        amplitude.setPath("fr.axonic.stimulation");
        amplitude.setCode("amplitude");
        amplitude.setUnit("mA");
        frequency=new AContinuousNumber();
        this.frequency.setLabel("Frequency");
        frequency.setPath("fr.axonic.stimulation");
        frequency.setCode("frequency");
        frequency.setUnit("Hz");
        this.duration=new AContinuousNumber();
        duration.setUnit("ms");
        duration.setPath("fr.axonic.stimulation");
        duration.setCode("duration");
        duration.setLabel("Duration");

        this.setLabel("Waveform Parameters");
        this.setCode("waveformParameters");
        this.setPath("fr.axonic.stimulation");
        super.init();
    }

    @XmlElement
    public ANumber getAmplitude() {
        return amplitude;
    }

    public void setAmplitudeValue(double amplitude) throws VerificationException {
        this.amplitude.setValue(amplitude);
    }

    @XmlElement
    public ANumber getFrequency() {
        return frequency;
    }

    public void setFrequencyValue(double frequency) throws VerificationException {
        this.frequency.setValue(frequency);
    }

    @XmlElement
    public ANumber getDuration() {
        return duration;
    }

    private void setAmplitude(AContinuousNumber amplitude) {
        this.amplitude = amplitude;
    }

    private void setFrequency(AContinuousNumber frequency) {
        this.frequency = frequency;
    }

    private void setDuration(AContinuousNumber duration) {
        this.duration = duration;
    }

    public void setDurationValue(int duration) throws VerificationException {
        this.duration.setValue(duration);
    }
}
