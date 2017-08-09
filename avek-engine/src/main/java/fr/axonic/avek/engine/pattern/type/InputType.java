package fr.axonic.avek.engine.pattern.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.engine.support.Support;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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

	@XmlTransient
	@JsonIgnore
	public boolean isPrimitiveInputType(){
		return Evidence.class.isAssignableFrom(type);
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof InputType)) return false;
		if (!super.equals(o)) return false;

		InputType<?> inputType = (InputType<?>) o;

		return name != null ? name.equals(inputType.name) : inputType.name == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}
}
