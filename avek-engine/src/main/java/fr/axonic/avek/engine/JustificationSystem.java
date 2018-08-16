package fr.axonic.avek.engine;

import fr.axonic.avek.engine.constraint.pattern.intra.ReApplicablePatternConstraint;
import fr.axonic.avek.engine.diagram.JustificationDiagram;
import fr.axonic.avek.engine.exception.AlreadyBuildingException;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.kernel.Artifact;
import fr.axonic.avek.engine.kernel.Assertion;
import fr.axonic.avek.engine.pattern.*;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.constraint.PatternConstraintException;
import fr.axonic.avek.engine.constraint.graph.NoCycleConstraint;
import fr.axonic.avek.engine.support.evidence.Element;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.engine.support.evidence.Hypothesis;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.validation.exception.VerificationException;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by cduffau on 04/08/16.
 */
@XmlRootElement
public class JustificationSystem<T extends PatternsBase> implements JustificationSystemAPI<T> {


    protected T patternsBase;
    protected JustificationDiagram justificationDiagram;
    //@XmlTransient
    private final static Logger LOGGER = LoggerFactory.getLogger(JustificationSystem.class);

    protected boolean autoSupportFillEnable =false;
    protected boolean versioningEnable=false;

    public JustificationSystem() {
        justificationDiagram =new JustificationDiagram();

    }
    public JustificationSystem(T patternsBase) throws VerificationException, WrongEvidenceException {
        this();
        this.patternsBase = patternsBase;
    }


    @Override
    @XmlElement
    @XmlElementWrapper
    public T getPatternsBase() {
        return patternsBase;
    }

    public void setPatternsBase(T patternsBase) {
        this.patternsBase = patternsBase;
    }


    private void setJustificationDiagram(JustificationDiagram justificationDiagram) {
        this.justificationDiagram=justificationDiagram;
    }

    @Override
    @XmlTransient
    public boolean validate() {
        if(patternsBase instanceof ListPatternsBase){
            return ((ListPatternsBase)patternsBase).getConstraints().stream().allMatch(constraint -> constraint.verify(justificationDiagram.getSteps()));

        }
        return true;
        }

    @Override
    @XmlTransient
    public List<Support> getUnusedAssertions(List<Support> supportsList) {
        List<Support> supports=new ArrayList<>(supportsList);
        supports.removeAll(justificationDiagram.getUsedAssertions());
        return supports;
    }


    @XmlTransient
    @Override
    public List<Pattern> getApplicablePatterns(List<Support> supports){
        List<Support> allSupports=new ArrayList<>(getUnusedAssertions(supports));
        justificationDiagram.getUsedAssertions().forEach(assertion -> allSupports.add((Support) assertion));

        Set<String> patterns=new HashSet<>(patternsBase.getPossiblePatterns(allSupports));
        if(patternsBase.getPatternsBaseType()==PatternsBaseType.PATTERN_DIAGRAM) {
            List<String> patternsAlreadyApply = justificationDiagram.getSteps().stream().map(justificationStep -> justificationStep.getPatternId()).collect(Collectors.toList());
            if(versioningEnable){
                for(String patternAlreadyApply : patternsAlreadyApply){
                    if(new ReApplicablePatternConstraint(patternsBase.getPattern(patternAlreadyApply),justificationDiagram.getSteps()).verify(allSupports)){
                        patterns.add(patternAlreadyApply);
                    }
                    else {
                        patterns.remove(patternAlreadyApply);
                    }
                }
            }
            else {
                patterns.removeAll(patternsAlreadyApply);
            }
            //patterns.addAll(patternsBase.getPossiblePatterns(getUnusedAssertions(supports)));

        }
        List<Pattern> patternList=patterns.stream().map(patternsBase::getPattern).collect(Collectors.toList());
        return patternList;
    }

    @Override
    public JustificationStep constructStep(Pattern pattern, List<Support> evidences, Conclusion conclusion) throws StepBuildingException, WrongEvidenceException, StrategyException {
        if(evidences==null || evidences.isEmpty()){
            throw new StepBuildingException("Need evidences");
        }
        if(conclusion==null){
            throw new StepBuildingException("Need a conclusion");
        }
        if(pattern==null){
            throw new StepBuildingException("Need a pattern");
        }
        if(patternsBase.getPatternsBaseType()==PatternsBaseType.PATTERN_DIAGRAM){
            boolean patternAlreadyApply=justificationDiagram.getSteps().stream().anyMatch(justificationStep -> justificationStep.getPatternId().equals(pattern.getId()));
            if(!versioningEnable && patternAlreadyApply) {
                throw new AlreadyBuildingException("Pattern already applied. Impossible to re-apply in a patterns base with justification pattern diagram");
            }
            else if(versioningEnable && patternAlreadyApply){
                int count=0;
                Optional<JustificationStep> step=justificationDiagram.getSteps().stream().filter(justificationStep -> justificationStep.getPatternId().equals(pattern.getId())).findFirst();
                for(Support assertion : step.get().getSupports()){
                        for (Support evidence : evidences) {
                            if (!evidence.equals(assertion) && evidence.equals(assertion,false)){
                                count++;
                                updateSupport(assertion,evidence.getElement());
                            }
                        }
                }
                if(count==0){
                    throw new AlreadyBuildingException("Pattern already applied with these artifacts versions on the assertions. Impossible to re-apply in a patterns base with justification pattern diagram");
                }
                else {
                    return step.get();
                }
            }


        }

        try{
            List<Support> usefulEvidences=filterUsefulSupports(pattern,evidences,conclusion);
            JustificationStep step=pattern.createStep(usefulEvidences,conclusion.clone());
            justificationDiagram.addStep(step);
            LOGGER.info("Supports : "+ usefulEvidences);
            LOGGER.info("Conclusion : "+ step.getConclusion());
            postStepCreated(step);
            return step;
        }
        catch (CloneNotSupportedException  e){
            throw new StepBuildingException("Problem during step creation ",e);
        }


    }

    private <T extends Element> void updateSupport(Support<T> support, T element){
        support.setElement(element);
    }

    protected void postStepCreated(JustificationStep step) throws CloneNotSupportedException, WrongEvidenceException {
        InputType<? extends Conclusion> evidenceRoleType=new InputType<>("",step.getConclusion().getClass());

        if(autoSupportFillEnable){
            //registeredEvidences.add(step.getConclusion().clone());
            //List<Support> evidencesToAdd=step.getSupports().stream().filter(supportRole -> supportRole.isPrimitiveInputType() && !registeredEvidences.contains(supportRole)).collect(Collectors.toList());
            //registeredEvidences.addAll(evidencesToAdd);
        }
    }

    protected List<Support> filterUsefulSupports(Pattern pattern, List<Support> supports, Conclusion conclusion) throws StepBuildingException {
        List<Support> usefulEvidences=pattern.filterUsefulEvidences(supports);
        if(autoSupportFillEnable && usefulEvidences.size()!=pattern.getInputTypes().size()){
            LOGGER.info("Missing supports. Trying to autofill");
            List<InputType> inputTypeList=pattern.filterNotFillInput(usefulEvidences);
            LOGGER.info("Found "+inputTypeList+ " to fill");
            List<Support> autoFillSupports=collectSupports(inputTypeList,supports);
            LOGGER.info("Found "+autoFillSupports+ ". Add them to supports");
            usefulEvidences.addAll(autoFillSupports);
        }
        if(versioningEnable){
            //usefulEvidences=usefulEvidences.stream().filter(supportRole -> supportRole.getElement()!=null && conclusion.getElement()!=null && ( supportRole.getElement().getVersion()==null || supportRole.getElement().getVersion().equals(conclusion.getElement().getVersion()))).collect(Collectors.toList());
        }
        return usefulEvidences;
    }

    private List<Support> collectSupports(List<InputType> inputTypes, List<Support> supports) throws StepBuildingException {
        List<Assertion> usedSupports=justificationDiagram.getUsedAssertions();
        usedSupports.addAll(getUnusedAssertions(supports));
        List<Support> collected=new ArrayList<>();
        for(InputType inputType:inputTypes){
            for(Assertion support: usedSupports){
                if(inputType.getType().getClassType().equals(support.getClass())){
                    if(!collected.contains(support)){
                        collected.add((Support) support);
                        break;
                    }
                }
            }
        }
        LOGGER.info(collected.toString());
        if(collected.size()!=inputTypes.size()){
            throw new StepBuildingException("Impossible to auto fill all supports");
        }

        return collected;
    }

    @Override
    public JustificationDiagram getJustificationDiagram() {
        return justificationDiagram;
    }


    @Override
    public void resolveHypothesis(JustificationStep step, Hypothesis hypothesis, Support support) throws WrongEvidenceException, PatternConstraintException {
        Support hypo=step.getSupports().stream().filter(evidenceRole -> evidenceRole instanceof Hypothesis && evidenceRole.equals(hypothesis)).collect(singletonCollector());
        Pattern pattern=patternsBase.getPattern(step.getPatternId());
        Optional<InputType> evidenceRoleTypeResult=pattern.getInputTypes().stream().filter(evidenceRoleType -> evidenceRoleType.check(hypo)).findAny();
        if(evidenceRoleTypeResult.isPresent()){
            Support res=evidenceRoleTypeResult.get().create(support);
            step.getSupports().remove(hypo);
            step.getSupports().add(res);
            NoCycleConstraint constraint=new NoCycleConstraint(step);
            if(!constraint.verify(justificationDiagram.getSteps())){
                step.getSupports().add(hypo);
                step.getSupports().remove(res);
                throw new PatternConstraintException("No cycle allowed, "+support+ " will create a cycle");
            }

        }
    }

    @Override
    @XmlTransient
    public boolean isComplete(){
        return justificationDiagram.getSteps().stream().anyMatch(justificationStep -> patternsBase.getObjective().check(justificationStep.getConclusion()));
    }

    @Override
    @XmlTransient
    public List<Pair<Pattern, JustificationStep>> matrix() {
        List<Pair<Pattern,JustificationStep>> matrix=new ArrayList<>();
        justificationDiagram.getSteps().forEach(justificationStep -> matrix.add(new Pair<>(patternsBase.getPattern(justificationStep.getPatternId()),justificationStep)));
        return matrix;
    }

    @Override
    public String toString() {
        return "JustificationSystem{" +
                "patternsBase=" + patternsBase +
                ", justificationDiagram=" + justificationDiagram +
                '}';
    }

    public static <T> Collector<T, ?, T> singletonCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }

}
