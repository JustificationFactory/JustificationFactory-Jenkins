package fr.axonic.avek.engine.strategy;

import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.Evidence;

import java.util.Map;

public abstract class HumanStrategy extends Strategy{

	private Comment comment;

	public void addComment(Comment comment){
		this.comment=comment;
	}

	public abstract  boolean check(Map<String, Evidence> evidenceRoles, Conclusion conclusion);

	}
