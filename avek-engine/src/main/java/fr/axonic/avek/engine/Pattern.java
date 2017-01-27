package fr.axonic.avek.engine;

import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.Evidence;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.avek.engine.evidence.Support;
import fr.axonic.avek.engine.strategy.Actor;
import fr.axonic.avek.engine.strategy.HumanStrategy;
import fr.axonic.avek.engine.strategy.Strategy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Pattern {
	
	private List<EvidenceRoleType> roleTypes;
	private Strategy strategy;
	private String name;
	private String id;
	private ConclusionType conclusionType;

	public Pattern(){

	}

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
					if(roleTypes.get(i).isEvidenceType(asList.get(j).getSupport())){
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
	
	public boolean checkConclusion(List<Support> asList, Conclusion conclusion) {
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
				if(evidenceRoleType.isEvidenceType(evidenceRole.getSupport())){
					evidenceRoleList.add(evidenceRole);
				}
			}
		}
		return evidenceRoleList;
	}

	//Should call applicable
	public Step createStep(List<EvidenceRole> evidenceList, Conclusion conclusion, Actor actor) throws StepBuildingException, StrategyException {
		if(applicable(evidenceList)){
			Step res= null;
			Strategy strategy=this.getStrategy();
			if(strategy instanceof HumanStrategy){
				if(actor!=null){
					((HumanStrategy) strategy).setActor(actor);
				}
				else {
					throw new StepBuildingException("Need an actor for the human strategy : "+strategy);

				}
			}
			res = new Step(id,strategy, evidenceList, conclusion);

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
	@XmlElement
	@XmlElementWrapper
	public List<EvidenceRoleType> getRoleTypes() {
		return roleTypes;
	}

	public void setRoleTypes(List<EvidenceRoleType> roleTypes) {
		this.roleTypes = roleTypes;
	}

	@XmlElement
	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement
	public ConclusionType getConclusionType() {
		return conclusionType;
	}

	public void setConclusionType(ConclusionType conclusionType) {
		this.conclusionType = conclusionType;
	}
}
