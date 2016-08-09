package fr.axonic.avek.engine.instance.evidence;

import fr.axonic.avek.engine.conclusion.Limit;
import fr.axonic.avek.engine.evidence.Element;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.engine.AVarHelper;
import fr.axonic.validation.exception.VerificationException;

import java.util.Arrays;

public class Stimulation extends Element implements Limit {

    private ARangedEnum<WaveformEnum> waveform;
    private WaveformParameter waveformParameter;
    private StimulationScheduler stimulationScheduler;


    public Stimulation() throws VerificationException {
        this(null,null);

    }

    public Stimulation(StimulationScheduler stimulationScheduler, WaveformParameter waveformParameter) throws VerificationException {
        super();
        this.stimulationScheduler = stimulationScheduler;
        this.waveformParameter = waveformParameter;
        this.waveform=new ARangedEnum<>();
        waveform.setLabel("Waveform");
        waveform.setCode("waveform");
        waveform.setRange(AVarHelper.transformToAVar(Arrays.asList(WaveformEnum.values())));
        waveform.setPath("fr.axonic.stimulation");

        this.waveformParameter=waveformParameter;
        this.stimulationScheduler=stimulationScheduler;
        this.setLabel("Stimulation");
        this.setCode("stimulation");
        this.setPath("fr.axonic");
        super.init();

    }

    public ARangedEnum<WaveformEnum> getWaveform() {
        return waveform;
    }

    public void setWaveformValue(WaveformEnum waveform) throws VerificationException {
        this.waveform.setValue(waveform);
    }
    private void setWaveform(ARangedEnum<WaveformEnum> waveform) {
        this.waveform = waveform;
    }

    public WaveformParameter getWaveformParameter() {
        return waveformParameter;
    }

    public void setWaveformParameter(WaveformParameter waveformParameter) {
        this.waveformParameter = waveformParameter;
    }

    public StimulationScheduler getStimulationScheduler() {
        return stimulationScheduler;
    }

    public void setStimulationScheduler(StimulationScheduler stimulationScheduler) {
        this.stimulationScheduler = stimulationScheduler;
    }

    @Override
    public String toString() {
        return "Stimulation{" +
                "waveform=" + waveform +
                ", waveformParameter=" + waveformParameter +
                ", stimulationScheduler=" + stimulationScheduler +
                '}';
    }
}
