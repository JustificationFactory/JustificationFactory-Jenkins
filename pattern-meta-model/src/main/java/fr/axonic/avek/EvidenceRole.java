package fr.axonic.avek;

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
}
