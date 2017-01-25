package fr.axonic.avek.engine.evidence;

import fr.axonic.avek.engine.conclusion.Conclusion;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
public class Evidence<T extends Element> extends Support<T> implements Cloneable{
    public Evidence(String name, T element) {
        super(name, element);
    }

    public Evidence() {
    }

    @Override
    public Support<T> clone() {
        return new Evidence<>(this.getName(),this.getElement());
    }
}
