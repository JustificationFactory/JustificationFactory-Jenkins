package fr.axonic.avek.engine.support.conclusion;

import fr.axonic.avek.engine.support.evidence.Element;
import fr.axonic.avek.engine.support.Support;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Conclusion<T extends Element> extends Support<T> implements Cloneable {

	protected List<Restriction> restrictions;

	public Conclusion(String name, T element) {
		super(name, element);
	}

	public Conclusion() {
		super();
		restrictions=new ArrayList<>();
	}
	
	public Conclusion(T element) {
		this(null,element);
	}

	public Conclusion(Conclusion conclusion) {
		super(conclusion);
		this.restrictions=new ArrayList<>(conclusion.restrictions);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Conclusion)) return false;
		if (!super.equals(o)) return false;

		Conclusion<?> that = (Conclusion<?>) o;

		return restrictions != null ? restrictions.equals(that.restrictions) : that.restrictions == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (restrictions != null ? restrictions.hashCode() : 0);
		return result;
	}

	@Override
	public Conclusion clone() throws CloneNotSupportedException {
		Conclusion conclusion= (Conclusion) super.clone();
		conclusion.restrictions =this.restrictions;
		return conclusion;
	}

	@Override
	public String toString() {
		return super.toString().substring(0,super.toString().length()-1)+
				", restrictions=" + restrictions +
				'}';
	}

	@XmlAnyElement
	@XmlElementWrapper
	public List<Restriction> getRestrictions() {
		return restrictions;
	}

	private void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
	}
}
