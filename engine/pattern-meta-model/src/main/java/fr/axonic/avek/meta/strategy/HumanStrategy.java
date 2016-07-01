package fr.axonic.avek.meta.strategy;

import fr.axonic.avek.meta.conclusion.Conclusion;
import fr.axonic.avek.meta.evidence.Evidence;

import java.util.Map;

public abstract class HumanStrategy extends Strategy{
		abstract  boolean check(Map<String,Evidence> evidenceRoles, Conclusion conclusion);

	}
