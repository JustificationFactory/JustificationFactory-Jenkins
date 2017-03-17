package fr.axonic.avek.engine.pattern;

import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.strategy.Actor;
import fr.axonic.avek.engine.strategy.HumanStrategy;
import fr.axonic.avek.engine.strategy.Strategy;
import fr.axonic.avek.engine.pattern.type.OutputType;
import fr.axonic.avek.engine.pattern.type.InputType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Pattern {

	private String id;
	private String name;
	private Strategy strategy;
	private List<InputType> inputTypes;
	private OutputType outputType;

	public Pattern(){

	}

	public Pattern(String id,String aName, Strategy aStrategy, List<InputType> roleTypeList,
			OutputType aConclusionExperimentationType) {
		this.id=id;
		name = aName;
		strategy = aStrategy;
		inputTypes = roleTypeList;
		outputType = aConclusionExperimentationType;
	}
	public Pattern(String aName, Strategy aStrategy, List<InputType> roleTypeList,
				   OutputType aConclusionExperimentationType) {
		this("0",aName,aStrategy,roleTypeList,aConclusionExperimentationType);
	}

	public boolean applicable(List<SupportRole> asList) {
		List<InputType> evidenceRoleTypesUsed=new ArrayList<>();
			for (int i = 0; i < inputTypes.size(); i++) {
				for (SupportRole anAsList : asList) {
					if (inputTypes.get(i).check(anAsList.getSupport()) && !evidenceRoleTypesUsed.contains(inputTypes.get(i))) {
						evidenceRoleTypesUsed.add(inputTypes.get(i));
						if (evidenceRoleTypesUsed.size() == inputTypes.size()) {
							return true;
						}
					}
				}
			}
		return false;
	}
	
	public boolean checkConclusion(List<Support> asList, Conclusion conclusion) {
		if(!outputType.check(conclusion)){
			return false;
		}
		if(inputTypes.size()==asList.size()){
			for(int i = 0; i< inputTypes.size(); i++){
				InputType roleType= inputTypes.get(i);
				if(!roleType.check(asList.get(i))){
					return false;
				}
			}
		}


		return true;
	}

	public List<SupportRole> filterUsefulEvidences(List<SupportRole> supportRoles){
		List<SupportRole> supportRoleList =new ArrayList<>();
		for(SupportRole supportRole : supportRoles){
			for(InputType evidenceRoleType: inputTypes){
				if(evidenceRoleType.check(supportRole.getSupport())){
					if(!supportRoleList.contains(supportRole)){
						supportRoleList.add(supportRole);
					}

				}
			}
		}
		return supportRoleList;
	}

	//Should call applicable
	public Step createStep(List<SupportRole> evidenceList, Conclusion conclusion, Actor actor) throws StepBuildingException, StrategyException {
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

			if(checkConclusion(SupportRole.translateToEvidence(evidenceList),conclusion)){
				return res;
			}
            throw new StepBuildingException("There is a issue to  apply the pattern "+name);
		}
		throw new StepBuildingException("Theses evidences and conclusion are not matching with the pattern "+name);
	}

	@XmlElement
	@XmlElementWrapper
	public List<InputType> getInputTypes() {
		return inputTypes;
	}

	public void setInputTypes(List<InputType> inputTypes) {
		this.inputTypes = inputTypes;
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
	public OutputType getOutputType() {
		return outputType;
	}

	public void setOutputType(OutputType outputType) {
		this.outputType = outputType;
	}

	@Override
	public String toString() {
		return "Pattern{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", strategy=" + strategy +
				", inputTypes=" + inputTypes +
				", outputType=" + outputType +
				'}';
	}
}
