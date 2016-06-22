package fr.axonic.avek;

public class EvidenceRoleType<T extends Evidence>{

	private boolean optionnal = false;
	private String name;
	private Class evidenceType;
	public EvidenceRoleType(String string, Class<T> classEvidence	){
	evidenceType = classEvidence;
	}
	public EvidenceRole create(Evidence evidence) {
		if (evidence.getClass().equals(evidenceType))
				return  new EvidenceRole(this.name, evidence);
		
		return null;
	}


}
