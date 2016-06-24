package fr.axonic.avek.meta.strategy;

import fr.axonic.avek.meta.conclusion.Conclusion;
import fr.axonic.avek.meta.evidence.Evidence;

import java.util.Map;


public abstract class ComputedStrategy extends Strategy{
	public abstract Conclusion createConclusion(Map<String,Evidence> evidenceRoles);

}
