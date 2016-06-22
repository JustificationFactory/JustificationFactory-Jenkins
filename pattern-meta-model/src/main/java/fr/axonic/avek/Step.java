package fr.axonic.avek;

import java.util.List;
import java.util.Map;


public class Step {

	@Override
	public String toString() {
		return "Step [evidences=" + evidences + ", pattern=" + pattern + "]";
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

}
