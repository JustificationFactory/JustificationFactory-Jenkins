package fr.axonic.avek.engine.strategy;

import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.Evidence;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@XmlRootElement
public abstract class ComputedStrategy extends Strategy{
	public abstract Conclusion createConclusion(Map<String,Evidence> evidenceRoles);

}
