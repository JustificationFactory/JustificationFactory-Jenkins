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
        this(new StimulationScheduler(),new WaveformParameter());

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
        setProperty(AVEKAStructureProperty.WAVEFORM.name(),waveform);
    }

    public WaveformParameter getWaveformParameter() {
        return waveformParameter;
    }

    public void setWaveformParameter(WaveformParameter waveformParameter) {
        setProperty(AVEKAStructureProperty.WAVEFORM_PARAMETER.name(),waveformParameter);
    }

    public StimulationScheduler getStimulationScheduler() {
        return stimulationScheduler;
    }

    public void setStimulationScheduler(StimulationScheduler stimulationScheduler) {
        setProperty(AVEKAStructureProperty.STIMULATION_SCHEDULER.name(),stimulationScheduler);
    }

    protected Object getPropertyValue(String propertyName) {
        Object result;
        try {
            switch (AVEKAStructureProperty.valueOf(propertyName)) {
                case WAVEFORM_PARAMETER: {
                    result = waveformParameter;
                }
                break;
                case STIMULATION_SCHEDULER: {
                    result = stimulationScheduler;
                }
                break;
                case WAVEFORM: {
                    result = waveform;
                    break;
                }
                default: {
                    result = super.getPropertyValue(propertyName);
                }
            }
        }
        catch (IllegalArgumentException e){
            result=super.getPropertyValue(propertyName);
        }

        return result;
    }

    protected boolean isPropertyVerifiable(String propertyName) {
        boolean result;
        try{
            switch (AVEKAStructureProperty.valueOf(propertyName)) {
                case WAVEFORM_PARAMETER:
                case STIMULATION_SCHEDULER:
                case WAVEFORM:{
                    result = true;
                }
                break;
                default: {
                    result = super.isPropertyVerifiable(propertyName);
                }
            }
        }
        catch (IllegalArgumentException e){
            result = super.isPropertyVerifiable(propertyName);
        }
        return result;
    }

    protected void setPropertyValue(String propertyName, Object newPropertyValue) {
        try {
            switch (AVEKAStructureProperty.valueOf(propertyName)) {
                case WAVEFORM: {
                    waveform = (ARangedEnum<WaveformEnum>) newPropertyValue;
                }
                break;
                case WAVEFORM_PARAMETER:
                    waveformParameter = (WaveformParameter) newPropertyValue;
                    break;
                case STIMULATION_SCHEDULER: {
                    stimulationScheduler = (StimulationScheduler) newPropertyValue;
                }
                break;

                default: {
                    super.setPropertyValue(propertyName, newPropertyValue);
                }
            }
        }
        catch (IllegalArgumentException e){
            super.setPropertyValue(propertyName, newPropertyValue);
        }

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
