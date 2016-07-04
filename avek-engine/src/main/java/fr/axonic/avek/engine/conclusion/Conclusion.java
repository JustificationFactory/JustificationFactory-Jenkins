package fr.axonic.avek.engine.conclusion;

import fr.axonic.avek.engine.evidence.Element;
import fr.axonic.avek.engine.evidence.Evidence;

import java.util.List;


public class Conclusion<T extends Element> extends Evidence {

	public Conclusion(String name, T element) {
		super(name, element);
	}

	public Conclusion() {
		// TODO Auto-generated constructor stub
	}
	
	public Conclusion(Object o) {
		// TODO Auto-generated constructor stub
	}

	protected List<Limit> limits;
}
