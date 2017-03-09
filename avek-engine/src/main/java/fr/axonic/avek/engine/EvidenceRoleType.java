package fr.axonic.avek.engine;

import fr.axonic.avek.engine.evidence.Element;
import fr.axonic.avek.engine.evidence.Evidence;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.avek.engine.evidence.Support;

import javax.annotation.processing.SupportedOptions;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EvidenceRoleType<T extends Support>{

	private boolean optional = false;
	private String name;
	private Class evidenceType;

	public EvidenceRoleType() {
	}

	public EvidenceRoleType(String name, Class<T> classEvidence){
		this.name=name;
		this.evidenceType = classEvidence;
		this.optional=false;
	}
	public EvidenceRole create(Support evidence) throws WrongEvidenceException {
		if (evidence.getClass().equals(evidenceType)){
			return  new EvidenceRole(this.name, evidence);
		}
		throw new WrongEvidenceException(evidence+ " is not compatible with "+evidenceType);
	}

	@XmlElement
	public boolean isEvidenceType(Support support){
		return support.getElement().getClass().equals(evidenceType);
	}

	@XmlElement
	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optionnal) {
		this.optional = optionnal;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public Class getEvidenceType() {
		return evidenceType;
	}

	public void setEvidenceType(Class evidenceType) {
		this.evidenceType = evidenceType;
	}
}
