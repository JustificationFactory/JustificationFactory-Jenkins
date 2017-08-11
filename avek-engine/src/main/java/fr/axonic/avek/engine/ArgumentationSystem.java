package fr.axonic.avek.engine;

import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.exception.WrongObjectiveException;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.constraint.ArgumentationSystemConstraint;
import fr.axonic.avek.engine.constraint.PatternConstraintException;
import fr.axonic.avek.engine.constraint.graph.NoCycleConstraint;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.engine.support.evidence.Hypothesis;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.PatternsBase;
import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.engine.strategy.Actor;
import fr.axonic.avek.engine.strategy.Role;
import fr.axonic.avek.engine.pattern.type.OutputType;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.validation.exception.VerificationException;
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
public class ArgumentationSystem implements ArgumentationSystemAPI {

    private PatternsBase patternsBase;
    private OutputType objective;
    private List<SupportRole> baseEvidences = new ArrayList<>();
    private List<Step> steps;
    //@XmlTransient
    private final static Logger LOGGER = LoggerFactory.getLogger(ArgumentationSystem.class);

    public ArgumentationSystem() throws VerificationException, WrongEvidenceException {
        steps=new ArrayList<>();

    }
    public ArgumentationSystem(PatternsBase patternsBase, List<SupportRole> baseEvidences) throws VerificationException, WrongEvidenceException {
        this();
        this.patternsBase=patternsBase;
        this.baseEvidences=baseEvidences;
    }



    @Override
    public Pattern getPattern(String patternId) {
        return patternsBase.getPattern(patternId);
    }

    @Override
    @XmlElement
    @XmlElementWrapper
    public List<SupportRole> getBaseEvidences() {
        return baseEvidences;
    }

    @Override
    @XmlElement
    @XmlElementWrapper
    public PatternsBase getPatternsBase() {
        return patternsBase;
    }

    public void setPatternsBase(PatternsBase patternsBase) {
        this.patternsBase = patternsBase;
    }


    private void setBaseEvidences(List<SupportRole> baseEvidences) {
        this.baseEvidences = baseEvidences;
    }

    private void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    @XmlElement
    public OutputType getObjective() {
        return objective;
    }

    @Override
    public boolean validate() {
        return patternsBase.getConstraints().stream().allMatch(constraint -> constraint.verify(steps));
    }

    public void setObjective(OutputType objective) throws WrongObjectiveException {
        if(objective!=null){
            if(patternsBase.getPatterns().stream().filter(pattern -> pattern.getOutputType().equals(objective)).count()>=1){
                this.objective = objective;
            }
            else{
                throw new WrongObjectiveException();
            }
        }
    }

    @Override
    public Step constructStep(Pattern pattern, List<SupportRole> evidences, Conclusion conclusion) throws StepBuildingException, WrongEvidenceException, StrategyException {
        try{
            List<SupportRole> usefulEvidences=pattern.filterUsefulEvidences(evidences);
            Step step=pattern.createStep(usefulEvidences,conclusion.clone());
            steps.add(step);
            LOGGER.info(step.getConclusion().toString());
            InputType<? extends Conclusion> evidenceRoleType=new InputType<>("",step.getConclusion().getClass());
            baseEvidences.add(evidenceRoleType.create(step.getConclusion().clone()));
            return step;
        }
        catch (NullPointerException | CloneNotSupportedException  e){
            throw new StepBuildingException("Unknown pattern");
        }


    }

    @Override
    public List<Step> getSteps() {
        return steps;
    }

    /**private static void save(Object object, File file) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller m = context.createMarshaller();
        m.setProperty(MarshallerProperties.MEDIA_TYPE,
                "application/json");
        m.setProperty("jaxb.formatted.output", Boolean.TRUE);
        m.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, Boolean.FALSE);
        FileOutputStream fos = new FileOutputStream(file);
        m.marshal(object, fos);
        fos.close();


    }*/

    @Override
    public void resolveHypothesis(Step step, Hypothesis hypothesis, Support support) throws WrongEvidenceException, PatternConstraintException {
        SupportRole hypo=step.getEvidences().stream().filter(evidenceRole -> evidenceRole.getSupport() instanceof Hypothesis && evidenceRole.getSupport().equals(hypothesis)).collect(singletonCollector());
        Pattern pattern=patternsBase.getPattern(step.getPatternId());
        Optional<InputType> evidenceRoleTypeResult=pattern.getInputTypes().stream().filter(evidenceRoleType -> evidenceRoleType.check(hypo.getSupport())).findAny();
        if(evidenceRoleTypeResult.isPresent()){
            SupportRole res=evidenceRoleTypeResult.get().create(support);
            step.getEvidences().remove(hypo);
            step.getEvidences().add(res);
            NoCycleConstraint constraint=new NoCycleConstraint(step);
            if(!constraint.verify(steps)){
                step.getEvidences().add(hypo);
                step.getEvidences().remove(res);
                throw new PatternConstraintException("No cycle allowed, "+support+ " will create a cycle");
            }

        }
    }
    @Override
    public String toString() {
        return "ArgumentationSystem{" +
                "patternsBase=" + patternsBase +
                ", objective=" + objective +
                ", baseEvidences=" + baseEvidences +
                ", steps=" + steps +
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
