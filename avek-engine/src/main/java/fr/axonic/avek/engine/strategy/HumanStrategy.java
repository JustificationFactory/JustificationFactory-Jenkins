package fr.axonic.avek.engine.strategy;

import fr.axonic.avek.engine.StrategyException;
import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.Evidence;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;
@XmlRootElement
public abstract class HumanStrategy extends Strategy{

	private Comment comment;
	private Actor actor;
	private Role minimumRole;

	public HumanStrategy(String name,Rationale rationale, UsageDomain usageDomain, Role actorMinimumRole) {
		super(name,rationale, usageDomain);
		minimumRole=actorMinimumRole;
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

	public void setActor(Actor actor) throws StrategyException {
		if(!checkRole(actor)){
			throw new StrategyException(actor.getName()+" has a none acceptable role");
		}
		this.actor = actor;
	}

	public Role getMinimumRole() {
		return minimumRole;
	}

	public void setMinimumRole(Role minimumRole) {
		this.minimumRole = minimumRole;
	}

	private boolean checkRole(Actor actor){
		return minimumRole==null || minimumRole.ordinal()>=actor.getRole().ordinal();
	}
}


