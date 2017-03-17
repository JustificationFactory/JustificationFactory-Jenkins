package fr.axonic.avek.engine.pattern.type;

import fr.axonic.avek.engine.support.conclusion.Conclusion;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OutputType<T extends Conclusion> extends SupportType<T>{

	public OutputType() {
		super(null);
	}

	public OutputType(Class<T> conclusionClass) {
		super(conclusionClass);
	}
	

	public Conclusion create(Object o) {
		if (o.getClass().equals(type))
				return  new Conclusion(o);
		
		return null;
	}
}
