package fr.axonic.avek.engine.pattern;

import fr.axonic.avek.engine.constraint.StepConstraint;
import fr.axonic.avek.engine.constraint.pattern.intra.ApplicablePatternConstraint;
import fr.axonic.avek.engine.constraint.pattern.intra.StepConstructionConstraint;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.kernel.JustificationStepAPI;
import fr.axonic.avek.engine.kernel.StrategyAPI;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.strategy.Strategy;
import fr.axonic.avek.engine.pattern.type.OutputType;
import fr.axonic.avek.engine.pattern.type.InputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Pattern extends JustificationStepAPI<InputType,OutputType> {

	private final static Logger LOGGER = LoggerFactory.getLogger(Pattern.class);


	private String id;
	private String name;

	private List<StepConstraint> constructionConstraints;
	private ApplicablePatternConstraint applicablePatternConstraint=new ApplicablePatternConstraint(this);

	public Pattern(){
		this(null,null,null,null);
	}

	public Pattern(String id,String aName, Strategy aStrategy, List<InputType> roleTypeList,
			OutputType aConclusion) {
		super(roleTypeList,aStrategy, aConclusion);
		constructionConstraints=new ArrayList<>();
		constructionConstraints.add(new StepConstructionConstraint(this));
		this.id=id;
		name = aName;
	}
	public Pattern(String aName, Strategy aStrategy, List<InputType> roleTypeList,
				   OutputType aConclusionExperimentationType) {
		this("0",aName,aStrategy,roleTypeList,aConclusionExperimentationType);
	}

	boolean applicable(List<Support> supports) {
		return applicablePatternConstraint.verify(supports);
	}
	
	private boolean checkConclusion(JustificationStep step) {
		return constructionConstraints.stream().allMatch(stepConstraint -> stepConstraint.verify(step)) ;
	}

	public List<Support> filterUsefulEvidences(List<Support> supportRoles){
		List<Support> supportRoleList =new ArrayList<>();
		for(Support support : supportRoles){
			for(InputType evidenceRoleType: supports){
				if(evidenceRoleType.check(support)){
					if(!supportRoleList.contains(support)){
						supportRoleList.add(support);
					}

				}
			}
		}
		return supportRoleList;
	}
	public List<InputType> filterNotFillInput(List<Support> supportRoles){
		List<InputType> usedInputTypeList =new ArrayList<>();
		for(Support support : supportRoles){
			for(InputType evidenceRoleType: supports){
				if(evidenceRoleType.check(support)){
					if(!usedInputTypeList.contains(support)){
						usedInputTypeList.add(evidenceRoleType);
					}

				}
			}
		}
		List<InputType> inputTypesRes=new ArrayList<>(this.supports);
		inputTypesRes.removeAll(usedInputTypeList);
		LOGGER.info(inputTypesRes.toString());
		return inputTypesRes;
	}

	public JustificationStep createStep(List<Support> evidenceList, Conclusion conclusion) throws StepBuildingException {
		if(applicable(evidenceList)){
			JustificationStep res= null;
			Strategy strategy= (Strategy) this.getStrategy();
			res = new JustificationStep(id,strategy, evidenceList, conclusion);

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
		return supports;
	}

	public void setInputTypes(List<InputType> inputTypes) {
		this.supports = inputTypes;
	}

	@XmlElement
	public StrategyAPI getStrategy() {
		return super.getStrategy();
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
		return conclusion;
	}

	public void setOutputType(OutputType outputType) {
		this.conclusion = outputType;
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
				", inputTypes=" + supports +
				", outputType=" + conclusion +
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
		if (supports != null ? !supports.equals(pattern.supports) : pattern.supports != null) return false;
		return conclusion != null ? conclusion.equals(pattern.conclusion) : pattern.conclusion == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (strategy != null ? strategy.hashCode() : 0);
		result = 31 * result + (supports != null ? supports.hashCode() : 0);
		result = 31 * result + (conclusion != null ? conclusion.hashCode() : 0);
		return result;
	}

	@Override
	public boolean isTerminal() {
		return false;
	}

	@Override
	public List<JustificationStepAPI> conformsTo() {
		return null;
	}
}
