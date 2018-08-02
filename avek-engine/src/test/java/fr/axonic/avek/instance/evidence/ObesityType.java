package fr.axonic.avek.instance.evidence;

import fr.axonic.base.engine.AEnumItem;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cduffau on 02/08/16.
 */
@XmlRootElement
public enum ObesityType implements AEnumItem{
    ANDROID("android","Android"), GYNOID("gynoid","Gynoid"), MIXED("mixed","Mixed");

    private String code, path, label;

    ObesityType(String code, String label) {
        this.code = code;
        this.path = "fr.axonic.subject.pathology.obesityType";
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
