package fr.axonic.avek.engine.instance.evidence;

import fr.axonic.avek.engine.conclusion.Limit;
import fr.axonic.avek.engine.evidence.Element;
import fr.axonic.base.ANumber;
import fr.axonic.base.AString;
import fr.axonic.validation.exception.VerificationException;

public class Stimulation extends Element implements Limit {

    private AString waveform;
    private ANumber amplitude;
    private ANumber frequency;
    private ANumber duration;

    public Stimulation() throws VerificationException {
        super();
        this.waveform=new AString();
        waveform.setLabel("Waveform");
        waveform.setCode("waveform");
        waveform.setPath("fr.axonic.stimulation");
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
        this.setLabel("Stimulation");
        this.setCode("stimulation");
        this.setPath("fr.axonic");
        super.init();
    }

    public AString getWaveform() {
        return waveform;
    }

    public void setWaveformValue(String waveform) throws VerificationException {
        this.waveform.setValue(waveform);
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

    private void setWaveform(AString waveform) {
        this.waveform = waveform;
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

    @Override
    public String toString() {
        return "Stimulation{" +
                "waveform=" + waveform +
                ", amplitude=" + amplitude +
                ", frequency=" + frequency +
                ", duration=" + duration +
                '}';
    }
}
