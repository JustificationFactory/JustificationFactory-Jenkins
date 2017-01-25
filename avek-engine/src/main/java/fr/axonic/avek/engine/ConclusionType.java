package fr.axonic.avek.engine;

import fr.axonic.avek.engine.conclusion.Conclusion;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConclusionType<T extends Conclusion> {

	private Class conclusionType;

	public ConclusionType() {
	}

	public ConclusionType(Class<T> conclusionClass) {
		conclusionType = conclusionClass;
	}
	

	public Conclusion create(Object o) {
		if (o.getClass().equals(conclusionType))
				return  new Conclusion(o);
		
		return null;
	}

	public boolean isConclusionType(Conclusion conclusion){
		return conclusion.getClass().equals(conclusionType);
	}

	@XmlElement
	private Class getConclusionType() {
		return conclusionType;
	}

	private void setConclusionType(Class conclusionType) {
		this.conclusionType = conclusionType;
	}
}
