package fr.axonic.avek.engine.strategy;

import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.Evidence;

import java.util.Map;

public abstract class HumanStrategy extends Strategy{
		abstract  boolean check(Map<String,Evidence> evidenceRoles, Conclusion conclusion);

	}
