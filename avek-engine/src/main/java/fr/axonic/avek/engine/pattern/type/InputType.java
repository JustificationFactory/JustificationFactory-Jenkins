package fr.axonic.avek.engine.pattern.type;

import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.engine.support.Support;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InputType<T extends Support> extends SupportType<T>{

	private String name;


	public InputType() {
		super(null);
	}

	public InputType(String name, Class<T> classEvidence){
		super(classEvidence);
		this.name=name;

	}
	public SupportRole create(Support evidence) throws WrongEvidenceException {
		if (evidence.getClass().equals(type)){
			return  new SupportRole(this.name, evidence);
		}
		throw new WrongEvidenceException(evidence+ " is not compatible with "+ type);
	}

	public boolean isPrimitiveInputType(){
		return type.isAssignableFrom(Evidence.class);
	}
	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
