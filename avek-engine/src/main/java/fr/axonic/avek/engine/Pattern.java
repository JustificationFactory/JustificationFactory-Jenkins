package fr.axonic.avek.engine;

import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.Evidence;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.avek.engine.strategy.Strategy;

import java.util.ArrayList;
import java.util.List;


public class Pattern {
	
	private List<EvidenceRoleType> roleTypes;
	private Strategy strategy;
	private String name;
	private String id;
	private ConclusionType conclusionType;

	public Pattern(String id,String aName, Strategy aStrategy, List<EvidenceRoleType> roleTypeList,
			ConclusionType aConclusionExperimentationType) {
		this.id=id;
		name = aName;
		strategy = aStrategy;
		roleTypes= roleTypeList;
		conclusionType = aConclusionExperimentationType;
	}
	public Pattern(String aName, Strategy aStrategy, List<EvidenceRoleType> roleTypeList,
				   ConclusionType aConclusionExperimentationType) {
		this("0",aName,aStrategy,roleTypeList,aConclusionExperimentationType);
	}

	public boolean applicable(List<EvidenceRole> asList) {
		List<EvidenceRoleType> evidenceRoleTypesUsed=new ArrayList<>();
			for (int i = 0; i < roleTypes.size(); i++) {
				for (int j = 0; j < asList.size(); j++) {
					if(roleTypes.get(i).isEvidenceType(asList.get(j).getEvidence())){
						evidenceRoleTypesUsed.add(roleTypes.get(i));
						if(evidenceRoleTypesUsed.size()==roleTypes.size()){
							return true;
						}
					}
				}
				if (roleTypes.get(i).isOptional() && !evidenceRoleTypesUsed.contains(roleTypes.get(i))){
					evidenceRoleTypesUsed.add(roleTypes.get(i));
					if(evidenceRoleTypesUsed.size()==roleTypes.size()){
						return true;
					}
				}
			}
		return false;
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

	public List<EvidenceRole> filterUsefullEvidences(List<EvidenceRole> evidenceRoles){
		List<EvidenceRole> evidenceRoleList=new ArrayList<>();
		for(EvidenceRole evidenceRole:evidenceRoles){
			for(EvidenceRoleType evidenceRoleType:roleTypes){
				if(evidenceRoleType.isEvidenceType(evidenceRole.getEvidence())){
					evidenceRoleList.add(evidenceRole);
				}
			}
		}
		return evidenceRoleList;
	}

	//Should call applicable
	public Step createStep(List<EvidenceRole> evidenceList, Conclusion conclusion) throws StepBuildingException {
		if(applicable(evidenceList)){
			Step res= null;
			res = new Step(this, evidenceList, conclusion);

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

	public List<EvidenceRoleType> getRoleTypes() {
		return roleTypes;
	}

	public void setRoleTypes(List<EvidenceRoleType> roleTypes) {
		this.roleTypes = roleTypes;
	}

	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ConclusionType getConclusionType() {
		return conclusionType;
	}

	public void setConclusionType(ConclusionType conclusionType) {
		this.conclusionType = conclusionType;
	}
}
