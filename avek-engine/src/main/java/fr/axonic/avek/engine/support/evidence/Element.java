package fr.axonic.avek.engine.support.evidence;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.axonic.avek.instance.evidence.Stimulation;
import fr.axonic.avek.instance.evidence.Subject;
import fr.axonic.avek.instance.evidence.WaveformEnum;
import fr.axonic.base.engine.AStructure;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by cduffau on 22/06/16.
 */
@XmlRootElement
@XmlSeeAlso({Stimulation.class, Subject.class})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Stimulation.class, name = "Stimulation"),

        @JsonSubTypes.Type(value = Subject.class, name = "Subject") }
)
public class Element extends AStructure {
}
