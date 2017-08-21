package fr.axonic.avek.engine.support.evidence;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.axonic.avek.engine.strategy.Actor;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.instance.evidence.Stimulation;
import fr.axonic.avek.instance.evidence.StimulationEvidence;
import fr.axonic.avek.instance.evidence.SubjectEvidence;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Evidence<T extends Element> extends Support<T> implements Cloneable{
    public Evidence(String name, T element) {
        super(name, element);
    }

    public Evidence() {
    }
}
