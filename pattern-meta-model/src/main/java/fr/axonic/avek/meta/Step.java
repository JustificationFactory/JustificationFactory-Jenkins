package fr.axonic.avek.meta;

import fr.axonic.avek.meta.conclusion.Conclusion;
import fr.axonic.avek.meta.evidence.EvidenceRole;

import java.util.List;


public class Step {

	@Override
	public String toString() {
		return "Step [evidence=" + evidences + ", pattern=" + pattern + "]";
	}
	private List<EvidenceRole> evidences;
	private Pattern pattern;
	private Conclusion conclusion;
	//L'ordre sera utilisé pour décider de l'association aux roles dans le cas où plusieurs evidence d'un même type sont necessaire au pattern
	public Step(Pattern pattern, List<EvidenceRole> evidenceRolelist, Conclusion conclusion) {
		this.pattern = pattern;
		this.evidences = evidenceRolelist;
		this.conclusion = conclusion;
	}

	public List<EvidenceRole> getEvidences() {
		return evidences;
	}

	public void setEvidences(List<EvidenceRole> evidences) {
		this.evidences = evidences;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public Conclusion getConclusion() {
		return conclusion;
	}

	public void setConclusion(Conclusion conclusion) {
		this.conclusion = conclusion;
	}
}
