package fr.axonic.avek.engine.evidence;

import java.util.ArrayList;
import java.util.List;

public class EvidenceRole {
	private String role;
	private Evidence evidence;
	
	public EvidenceRole(String name, Evidence evidenceNew) {
		role = name;
		evidence = evidenceNew;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Evidence getEvidence() {
		return evidence;
	}
	public void setEvidence(Evidence evidence) {
		this.evidence = evidence;
	}

	public static List<Evidence> translateToEvidence(List<EvidenceRole> evidenceRoles){
		List<Evidence> evidences=new ArrayList<Evidence>();
		for(EvidenceRole evidenceRole : evidenceRoles){
			evidences.add(evidenceRole.getEvidence());
		}
		return evidences;
	}
}
