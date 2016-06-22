package fr.axonic.avek;

import java.util.List;
import java.util.Map;


public class Pattern {
	
	private List<EvidenceRoleType> roleTypes;
	private Strategy strategy;
	private String name;
	private String id;
	private ConclusionType conclusionExperimentationType;

	public Pattern(String aName, Strategy aStrategy, List<EvidenceRoleType> roleTypeList, 
			ConclusionType aConclusionExperimentationType) {
		name = aName;
		strategy = aStrategy;
		roleTypes= roleTypeList;
		conclusionExperimentationType = aConclusionExperimentationType;
	}
	
	public boolean applicable(List<EvidenceRole> asList) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean checkConclusion(List<Evidence> asList,Conclusion conclusion) {
		// TODO Auto-generated method stub
		return false;
	}


	//Should call applicable
	public Step createStep(List<EvidenceRole> evidenceList, Conclusion conclusion) {
		return new Step(this, evidenceList,conclusion);
	}

	@Override
	public String toString() {
		return "Pattern [roleTypes=" + roleTypes + ", strategy=" + strategy
				+ ", name=" + name + ", id=" + id
				+ ", conclusionExperimentationType="
				+ conclusionExperimentationType + "]";
	}

}
