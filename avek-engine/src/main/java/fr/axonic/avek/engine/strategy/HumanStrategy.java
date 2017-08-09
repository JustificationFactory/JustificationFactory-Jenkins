package fr.axonic.avek.engine.strategy;

import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Evidence;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Map;
@XmlRootElement
@XmlType(name="humanStrategy")
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


