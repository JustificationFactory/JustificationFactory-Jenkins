package fr.axonic.avek.engine;

import fr.axonic.avek.engine.evidence.Element;
import fr.axonic.avek.engine.evidence.Evidence;
import fr.axonic.avek.engine.evidence.EvidenceRole;

public class EvidenceRoleType<T extends Element>{

	private boolean optional = false;
	private String name;
	private Class evidenceType;
	public EvidenceRoleType(String name, Class<T> classEvidence){
		this.name=name;
		this.evidenceType = classEvidence;
		this.optional=false;
	}
	public EvidenceRole create(Evidence evidence) throws WrongEvidenceException {
		if (evidence.getElement().getClass().equals(evidenceType)){
			return  new EvidenceRole(this.name, evidence);
		}
		throw new WrongEvidenceException(evidence+ " is not compatible with "+evidenceType);
	}

	public boolean isEvidenceType(Evidence evidence){
		return evidence.getElement().getClass().equals(evidenceType);
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optionnal) {
		this.optional = optionnal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class getEvidenceType() {
		return evidenceType;
	}

	public void setEvidenceType(Class evidenceType) {
		this.evidenceType = evidenceType;
	}
}
