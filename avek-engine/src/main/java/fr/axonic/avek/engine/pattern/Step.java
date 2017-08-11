package fr.axonic.avek.engine.pattern;

import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.engine.strategy.Strategy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Step {

	private String patternId;
	private List<SupportRole> evidences;
	private Strategy strategy;
	private Conclusion conclusion;


	private Step() {
	}

	public Step(String patternId, Strategy strategy, List<SupportRole> supportRolelist, Conclusion conclusion) {
		this.strategy = strategy;
		this.evidences = supportRolelist;
		this.conclusion = conclusion;
		this.patternId=patternId;
	}

	@XmlElement(name="evidenceRoles")
	@XmlElementWrapper
	public List<SupportRole> getEvidences() {
		return evidences;
	}

	public void setEvidences(List<SupportRole> evidences) {
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

	@XmlElement
	public String getPatternId() {
		return patternId;
	}

	public void setPatternId(String patternId) {
		this.patternId = patternId;
	}

	@Override
	public String toString() {
		return "Step{" +
				"patternId='" + patternId + '\'' +
				", evidences=" + evidences +
				", strategy=" + strategy +
				", conclusion=" + conclusion +
				'}';
	}
}
