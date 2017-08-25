package fr.axonic.avek.instance.avek.strategy;

import fr.axonic.avek.engine.strategy.*;


public class TreatStrategy extends HumanStrategy {

	public TreatStrategy(Rationale rationale, UsageDomain usageDomain) {
		super("Treat",rationale, usageDomain);
	}

	public TreatStrategy() {
		this(null,null);
	}
// createConclusion according to Evidences
	/**public Conclusion createConclusion(Map<String,Evidence> evidenceRoles){
		return new ExperimentationConclusion((Subject)evidenceRoles.get("subject").getElement(),(Stimulation)evidenceRoles.get("stimulation").getElement());
	}
	//	new Conclusion();
	//}*/

}
