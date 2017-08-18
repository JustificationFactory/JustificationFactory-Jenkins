package fr.axonic.avek.engine.pattern;

import fr.axonic.avek.engine.constraint.StepConstraint;
import fr.axonic.avek.engine.constraint.pattern.intra.ApplicablePatternConstraint;
import fr.axonic.avek.engine.constraint.pattern.intra.StepConstructionConstraint;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.SupportRole;
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

	private List<StepConstraint> constructionConstraints;
	private ApplicablePatternConstraint applicablePatternConstraint=new ApplicablePatternConstraint(this);

	public Pattern(){
		constructionConstraints=new ArrayList<>();
		constructionConstraints.add(new StepConstructionConstraint(this));
	}

	public Pattern(String id,String aName, Strategy aStrategy, List<InputType> roleTypeList,
			OutputType aConclusionExperimentationType) {
		this();
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

	boolean applicable(List<SupportRole> supportRoles) {
		return applicablePatternConstraint.verify(supportRoles);
	}
	
	private boolean checkConclusion(Step step) {
		return constructionConstraints.stream().allMatch(stepConstraint -> stepConstraint.verify(step)) ;
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

	public Step createStep(List<SupportRole> evidenceList, Conclusion conclusion) throws StepBuildingException, StrategyException {
		if(applicable(evidenceList)){
			Step res= null;
			Strategy strategy=this.getStrategy();
			res = new Step(id,strategy, evidenceList, conclusion);

			if(checkConclusion(res)){
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

	public void addConstructionConstraint(StepConstraint stepConstraint){
		constructionConstraints.add(stepConstraint);
	}

	@Override
	public String toString() {
		return "Pattern{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", strategy=" + strategy +
				", inputTypes=" + inputTypes +
				", outputType=" + outputType +
				", constructionConstraints=" + constructionConstraints +
				", applicablePatternConstraint=" + applicablePatternConstraint +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Pattern)) return false;

		Pattern pattern = (Pattern) o;

		if (id != null ? !id.equals(pattern.id) : pattern.id != null) return false;
		if (name != null ? !name.equals(pattern.name) : pattern.name != null) return false;
		if (strategy != null ? !strategy.equals(pattern.strategy) : pattern.strategy != null) return false;
		if (inputTypes != null ? !inputTypes.equals(pattern.inputTypes) : pattern.inputTypes != null) return false;
		return outputType != null ? outputType.equals(pattern.outputType) : pattern.outputType == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (strategy != null ? strategy.hashCode() : 0);
		result = 31 * result + (inputTypes != null ? inputTypes.hashCode() : 0);
		result = 31 * result + (outputType != null ? outputType.hashCode() : 0);
		return result;
	}
}
