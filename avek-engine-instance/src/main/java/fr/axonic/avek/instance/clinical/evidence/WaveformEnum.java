package fr.axonic.avek.instance.clinical.evidence;

import fr.axonic.base.engine.AEnumItem;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cduffau on 09/08/16.
 */
@XmlRootElement
public enum WaveformEnum implements AEnumItem{
    RECTANGULAR("rectangular","Rectangular"), SINUS("sinus","Sinus"), RAMP("ramp","Ramp");
    private String code, path, label;

    WaveformEnum(String code, String label) {
        this.code = code;
        this.path = "fr.axonic.stimulation.waveform";
        this.label = label;
    }

    private void setCode(String code) {
        this.code = code;
    }

    private void setPath(String path) {
        this.path = path;
    }

    private void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public int getIndex() {
        return ordinal();
    }
}
