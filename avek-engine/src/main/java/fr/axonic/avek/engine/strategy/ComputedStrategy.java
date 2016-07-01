package fr.axonic.avek.engine.strategy;

import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.Evidence;

import java.util.Map;


public abstract class ComputedStrategy extends Strategy{
	public abstract Conclusion createConclusion(Map<String,Evidence> evidenceRoles);

}
