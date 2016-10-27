package fr.axonic.avek.engine.evidence;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
@XmlRootElement
public class EvidenceRole {
	private String role;
	private Evidence evidence;

	private EvidenceRole() {
	}

	public EvidenceRole(String name, Evidence evidenceNew) {
		role = name;
		evidence = evidenceNew;
	}
	@XmlElement
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	@XmlElement
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

	@Override
	public String toString() {
		return "EvidenceRole{role="+role+", evidence="+evidence+"}";
	}
}
