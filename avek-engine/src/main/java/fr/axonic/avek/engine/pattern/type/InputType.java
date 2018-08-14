package fr.axonic.avek.engine.pattern.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.support.evidence.Element;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.engine.support.Support;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class InputType<T extends Support> extends SupportType<T>{

	private String name;


	public InputType() {
		super();
	}

	public InputType(String name, Class<T> classEvidence){
		super(new Type<T>(classEvidence,name));
		this.name=name;

	}

	public InputType( String name, Type type) {
		super(type);
		this.name = name;
	}

	public Support create(Support evidence) throws WrongEvidenceException {
		if (evidence.getClass().equals(type.getClassType())){
			evidence.setName(name);
			return  evidence;
		}
		throw new WrongEvidenceException(evidence+ " is not compatible with "+ type);
	}

	public Support create(Element artifact) throws WrongEvidenceException {
		try {
			Support support= (Support) type.getClassType().newInstance();
			support.setElement(artifact);
			support.setName(name);
			return support;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new WrongEvidenceException(artifact+ " is not compatible with "+ type);

		}
	}

	@XmlTransient
	@JsonIgnore
	public boolean isPrimitiveInputType(){
		return Evidence.class.isAssignableFrom(getType().getClassType());
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
