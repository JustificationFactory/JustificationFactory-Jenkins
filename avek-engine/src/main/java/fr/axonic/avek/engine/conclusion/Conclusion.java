package fr.axonic.avek.engine.conclusion;

import fr.axonic.avek.engine.evidence.Element;
import fr.axonic.avek.engine.evidence.Evidence;

import java.util.List;


public class Conclusion<T extends Element> extends Evidence implements Cloneable {

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

	@Override
	public Conclusion<T> clone() {
		Conclusion<T> conclusion=new Conclusion<>(this.getName(), (T) this.getElement());
		conclusion.limits=this.limits;
		return conclusion;
	}
}
