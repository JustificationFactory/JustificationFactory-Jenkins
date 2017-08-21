package fr.axonic.avek.engine.support.conclusion;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.axonic.avek.engine.strategy.Actor;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.instance.conclusion.ExperimentationConclusion;
import fr.axonic.avek.instance.evidence.Stimulation;
import fr.axonic.avek.instance.evidence.StimulationEvidence;
import fr.axonic.avek.instance.evidence.Subject;
import fr.axonic.avek.instance.evidence.SubjectEvidence;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Subject.class, name="Subject"),
        @JsonSubTypes.Type(value = Stimulation.class, name="Stimulation")}
)
public interface Restriction {

}
