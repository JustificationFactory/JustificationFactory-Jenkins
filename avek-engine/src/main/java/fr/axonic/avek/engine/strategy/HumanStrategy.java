package fr.axonic.avek.engine.strategy;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.instance.conclusion.ExperimentationConclusion;
import fr.axonic.avek.instance.strategy.EstablishEffectStrategy;
import fr.axonic.avek.instance.strategy.GeneralizeStrategy;
import fr.axonic.avek.instance.strategy.TreatStrategy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import java.util.Map;
@XmlRootElement
@XmlSeeAlso({TreatStrategy.class, EstablishEffectStrategy.class, GeneralizeStrategy.class})
public class HumanStrategy extends Strategy{

	private Comment comment;

	public HumanStrategy(String name,Rationale rationale, UsageDomain usageDomain) {
		super(name,rationale, usageDomain);
	}

	public HumanStrategy() {
	}

	public void addComment(Comment comment){
		this.comment=comment;
	}
	@XmlElement
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

}


