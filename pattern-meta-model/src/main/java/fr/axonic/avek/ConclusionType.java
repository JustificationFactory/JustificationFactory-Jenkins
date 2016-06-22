package fr.axonic.avek;

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
	
	

}
