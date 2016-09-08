package fr.axonic.avek.engine.conclusion;

import fr.axonic.avek.engine.evidence.Element;
import fr.axonic.avek.engine.evidence.Evidence;

import java.util.List;


public class Conclusion<T extends Element> extends Evidence implements Cloneable {

	protected List<Limit> limits;

	public Conclusion(String name, T element) {
		super(name, element);
	}

	public Conclusion() {
		// TODO Auto-generated constructor stub
	}
	
	public Conclusion(Object o) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Conclusion)) return false;
		if (!super.equals(o)) return false;

		Conclusion<?> that = (Conclusion<?>) o;

		return limits != null ? limits.equals(that.limits) : that.limits == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (limits != null ? limits.hashCode() : 0);
		return result;
	}

	@Override
	public Conclusion<T> clone() {
		Conclusion<T> conclusion=new Conclusion<>(this.getName(), (T) this.getElement());
		conclusion.limits=this.limits;
		return conclusion;
	}
}
