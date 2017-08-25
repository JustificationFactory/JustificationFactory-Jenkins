package fr.axonic.avek.engine.support.evidence;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.axonic.avek.instance.avek.evidence.Stimulation;
import fr.axonic.avek.instance.avek.evidence.Subject;
import fr.axonic.base.engine.AStructure;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by cduffau on 22/06/16.
 */
@XmlRootElement
@XmlSeeAlso({Stimulation.class, Subject.class})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type")

public abstract class Element extends AStructure {
}
