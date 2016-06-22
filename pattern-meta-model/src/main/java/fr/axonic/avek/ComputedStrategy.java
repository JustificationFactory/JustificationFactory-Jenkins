package fr.axonic.avek;

import java.util.Map;


public abstract class ComputedStrategy extends Strategy{
	abstract Conclusion  createConclusion(Map<String,Evidence> evidenceRoles);

}
