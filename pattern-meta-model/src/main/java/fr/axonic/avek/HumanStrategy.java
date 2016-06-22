package fr.axonic.avek;

import java.util.Map;

public abstract class HumanStrategy extends Strategy{
		abstract  boolean check(Map<String,Evidence> evidenceRoles, Conclusion conclusion);

	}
