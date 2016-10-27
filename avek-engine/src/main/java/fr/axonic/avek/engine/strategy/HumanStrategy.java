package fr.axonic.avek.engine.strategy;

import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.Evidence;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;
@XmlRootElement
public abstract class HumanStrategy extends Strategy{

	private Comment comment;
	private Actor actor;

	public HumanStrategy(String name,Rationale rationale, UsageDomain usageDomain) {
		super(name,rationale, usageDomain);
	}

	public HumanStrategy() {
	}

	public void addComment(Comment comment){
		this.comment=comment;
	}

	public abstract  boolean check(Map<String, Evidence> evidenceRoles, Conclusion conclusion);

	@XmlElement
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	@XmlElement
	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}
}


