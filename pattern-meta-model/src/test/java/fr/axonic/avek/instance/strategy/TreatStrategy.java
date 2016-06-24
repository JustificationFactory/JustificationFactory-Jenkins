package fr.axonic.avek.instance.strategy;

import fr.axonic.avek.meta.conclusion.Conclusion;
import fr.axonic.avek.meta.evidence.Evidence;
import fr.axonic.avek.instance.conclusion.ExperimentationConclusion;
import fr.axonic.avek.instance.evidence.Stimulation;
import fr.axonic.avek.instance.evidence.Subject;
import fr.axonic.avek.meta.strategy.ComputedStrategy;

import java.util.Map;


public class TreatStrategy extends ComputedStrategy {
	
	// createConclusion according to Evidences
	public Conclusion createConclusion(Map<String,Evidence> evidenceRoles){
		return new ExperimentationConclusion((Subject)evidenceRoles.get("subject").getElement(),(Stimulation)evidenceRoles.get("stimulation").getElement());
	}
	//	new Conclusion();
	//}

}
