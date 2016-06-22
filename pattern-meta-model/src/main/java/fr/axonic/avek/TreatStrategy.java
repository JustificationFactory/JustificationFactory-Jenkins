package fr.axonic.avek;

import java.util.List;
import java.util.Map;


public class TreatStrategy extends ComputedStrategy {
	
	// createConclusion according to Evidences
	Conclusion createConclusion(Map<String,Evidence> evidenceRoles){
		return new Experimentation((Subject)evidenceRoles.get("subject"),(Stimulation)evidenceRoles.get("stimulation"));
	}
	//	new Conclusion();
	//}

}
