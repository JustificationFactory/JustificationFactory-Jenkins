package fr.axonic.avek.engine.conclusion;

import fr.axonic.avek.engine.evidence.Element;
import fr.axonic.avek.engine.evidence.Evidence;
import fr.axonic.avek.engine.evidence.Support;
import fr.axonic.avek.engine.instance.conclusion.EstablishEffectConclusion;
import fr.axonic.avek.engine.instance.conclusion.ExperimentationConclusion;
import fr.axonic.avek.engine.instance.conclusion.GeneralizationConclusion;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlSeeAlso({ExperimentationConclusion.class, EstablishEffectConclusion.class, GeneralizationConclusion.class})
public class Conclusion<T extends Element> extends Support<T> implements Cloneable {

	protected List<Restriction> restrictions;

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

		return restrictions != null ? restrictions.equals(that.restrictions) : that.restrictions == null;

	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (restrictions != null ? restrictions.hashCode() : 0);
		return result;
	}

	@Override
	public Conclusion<T> clone() {
		Conclusion<T> conclusion=new Conclusion<>(this.getName(), (T) this.getElement());
		conclusion.restrictions =this.restrictions;
		return conclusion;
	}

	@XmlAnyElement
	@XmlElementWrapper
	public List<Restriction> getRestrictions() {
		return restrictions;
	}
}
