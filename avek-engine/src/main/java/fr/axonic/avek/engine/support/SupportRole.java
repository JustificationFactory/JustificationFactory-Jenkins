package fr.axonic.avek.engine.support;

import fr.axonic.avek.engine.support.evidence.Evidence;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
@XmlRootElement
public class SupportRole {
	private String role;
	private Support support;

	private SupportRole() {
	}

	public SupportRole(String name, Support evidenceNew) {
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
	public void setSupport(Support support) {
		this.support = support;
	}

	public static List<Support> translateToSupport(List<SupportRole> supportRoles){
		List<Support> evidences=new ArrayList<>();
		for(SupportRole supportRole : supportRoles){
			evidences.add(supportRole.getSupport());
		}
		return evidences;
	}

	@Override
	public String toString() {
		return "SupportRole{role="+role+", support="+ support +"}";
	}
}
