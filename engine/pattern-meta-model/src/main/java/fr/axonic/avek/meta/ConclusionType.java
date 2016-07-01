package fr.axonic.avek.meta;

import fr.axonic.avek.meta.conclusion.Conclusion;
import fr.axonic.avek.meta.evidence.Evidence;

public class ConclusionType<T extends Conclusion> {

	private Class conclusionType;
	
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
	
	

}
