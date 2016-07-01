package fr.axonic.avek.meta;

import fr.axonic.avek.meta.conclusion.Conclusion;
import fr.axonic.avek.meta.evidence.Evidence;
import fr.axonic.avek.meta.evidence.EvidenceRole;
import fr.axonic.avek.meta.strategy.Strategy;

import java.util.List;


public class Pattern {
	
	private List<EvidenceRoleType> roleTypes;
	private Strategy strategy;
	private String name;
	private String id;
	private ConclusionType conclusionType;

	public Pattern(String aName, Strategy aStrategy, List<EvidenceRoleType> roleTypeList, 
			ConclusionType aConclusionExperimentationType) {
		name = aName;
		strategy = aStrategy;
		roleTypes= roleTypeList;
		conclusionType = aConclusionExperimentationType;
	}
	
	public boolean applicable(List<EvidenceRole> asList) {
		if(roleTypes.size()==asList.size()){
			for(int i=0;i<roleTypes.size();i++){
				EvidenceRoleType roleType=roleTypes.get(i);
				if(!roleType.isEvidenceType(asList.get(i).getEvidence())){
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean checkConclusion(List<Evidence> asList, Conclusion conclusion) {
		if(conclusionType.isConclusionType(conclusion)){
			return false;
		}
		if(roleTypes.size()==asList.size()){
			for(int i=0;i<roleTypes.size();i++){
				EvidenceRoleType roleType=roleTypes.get(i);
				if(!roleType.isEvidenceType(asList.get(i))){
					return false;
				}
			}
		}


		return true;
	}


	//Should call applicable
	public Step createStep(List<EvidenceRole> evidenceList, Conclusion conclusion) throws StepBuildingException {
		if(applicable(evidenceList)){
			Step res=new Step(this, evidenceList,conclusion);
			if(checkConclusion(EvidenceRole.translateToEvidence(evidenceList),conclusion)){
				return res;
			}
            throw new StepBuildingException("There is a issue to  apply the pattern "+name);
		}
		throw new StepBuildingException("Theses evidences and conclusion are not matching with the pattern "+name);
	}

	@Override
	public String toString() {
		return "Pattern [roleTypes=" + roleTypes + ", strategy=" + strategy
				+ ", name=" + name + ", id=" + id
				+ ", conclusionType="
				+ conclusionType + "]";
	}

}
