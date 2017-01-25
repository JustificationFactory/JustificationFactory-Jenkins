package fr.axonic.avek.engine.evidence;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
@XmlRootElement
public class EvidenceRole {
	private String role;
	private Support support;

	private EvidenceRole() {
	}

	public EvidenceRole(String name, Support evidenceNew) {
		role = name;
		support = evidenceNew;
	}
	@XmlElement
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	@XmlElement
	public Support getSupport() {
		return support;
	}
	public void setSupport(Evidence support) {
		this.support = support;
	}

	public static List<Support> translateToEvidence(List<EvidenceRole> evidenceRoles){
		List<Support> evidences=new ArrayList<>();
		for(EvidenceRole evidenceRole : evidenceRoles){
			evidences.add(evidenceRole.getSupport());
		}
		return evidences;
	}

	@Override
	public String toString() {
		return "EvidenceRole{role="+role+", support="+ support +"}";
	}
}
