package fr.axonic.avek.engine;

import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.avek.engine.strategy.Strategy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Step {

	@Override
	public String toString() {
		return "Step [evidence=" + evidences + ", pattern=" + strategy + "]";
	}
	private List<EvidenceRole> evidences;
	private Strategy strategy;
	private Conclusion conclusion;
	//L'ordre sera utilisé pour décider de l'association aux roles dans le cas où plusieurs evidence d'un même type sont necessaire au pattern

	private Step() {
	}

	public Step(Strategy strategy, List<EvidenceRole> evidenceRolelist, Conclusion conclusion) {
		this.strategy = strategy;
		this.evidences = evidenceRolelist;
		this.conclusion = conclusion;
	}

	@XmlElement(name="evidenceRoles")
	@XmlElementWrapper
	public List<EvidenceRole> getEvidences() {
		return evidences;
	}

	public void setEvidences(List<EvidenceRole> evidences) {
		this.evidences = evidences;
	}

	@XmlElement
	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	@XmlElement
	public Conclusion getConclusion() {
		return conclusion;
	}

	public void setConclusion(Conclusion conclusion) {
		this.conclusion = conclusion;
	}
}
