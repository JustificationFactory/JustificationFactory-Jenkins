package fr.axonic.avek.engine.support.evidence;

import fr.axonic.avek.engine.support.Support;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Evidence<T extends Element> extends Support<T> implements Cloneable{
    public Evidence(String name, T element) {
        super(name, element);
    }

    public Evidence() {
    }
}
