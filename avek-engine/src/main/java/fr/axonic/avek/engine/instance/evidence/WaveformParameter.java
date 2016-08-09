package fr.axonic.avek.engine.instance.evidence;

import fr.axonic.base.ANumber;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.engine.AStructure;
import fr.axonic.validation.exception.VerificationException;

/**
 * Created by cduffau on 09/08/16.
 */
public class WaveformParameter extends AStructure{
    private ANumber amplitude;
    private ANumber frequency;
    private ANumber duration;

    public WaveformParameter() throws VerificationException {
        super();
        this.amplitude=new ANumber();
        amplitude.setLabel("Amplitude");
        amplitude.setPath("fr.axonic.stimulation");
        amplitude.setCode("amplitude");
        amplitude.setUnit("mA");
        frequency=new ANumber();
        this.frequency.setLabel("Frequency");
        frequency.setPath("fr.axonic.stimulation");
        frequency.setCode("frequency");
        frequency.setUnit("Hz");
        this.duration=new ANumber();
        duration.setUnit("ms");
        duration.setPath("fr.axonic.stimulation");
        duration.setCode("duration");
        duration.setLabel("Duration");

        this.setLabel("Waveform Parameters");
        this.setCode("waveformParameters");
        this.setPath("fr.axonic.stimulation");
        super.init();
    }

    public ANumber getAmplitude() {
        return amplitude;
    }

    public void setAmplitudeValue(double amplitude) throws VerificationException {
        this.amplitude.setValue(amplitude);
    }

    public ANumber getFrequency() {
        return frequency;
    }

    public void setFrequencyValue(double frequency) throws VerificationException {
        this.frequency.setValue(frequency);
    }

    public ANumber getDuration() {
        return duration;
    }

    private void setAmplitude(ANumber amplitude) {
        this.amplitude = amplitude;
    }

    private void setFrequency(ANumber frequency) {
        this.frequency = frequency;
    }

    private void setDuration(ANumber duration) {
        this.duration = duration;
    }

    public void setDurationValue(int duration) throws VerificationException {
        this.duration.setValue(duration);
    }
}
