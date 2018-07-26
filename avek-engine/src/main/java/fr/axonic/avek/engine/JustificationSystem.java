package fr.axonic.avek.engine;

import fr.axonic.avek.engine.diagram.JustificationDiagram;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.exception.WrongObjectiveException;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.constraint.PatternConstraintException;
import fr.axonic.avek.engine.constraint.graph.NoCycleConstraint;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.engine.support.evidence.Hypothesis;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.PatternsBase;
import fr.axonic.avek.engine.pattern.JustificationStep;
import fr.axonic.avek.engine.pattern.type.OutputType;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.validation.exception.VerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by cduffau on 04/08/16.
 */
@XmlRootElement
public class JustificationSystem implements JustificationSystemAPI {

    protected boolean autoSupportFillEnable =false;
    protected PatternsBase patternsBase;
    protected OutputType objective;
    protected List<SupportRole> baseEvidences = new ArrayList<>();
    protected JustificationDiagram justificationDiagram;
    //@XmlTransient
    private final static Logger LOGGER = LoggerFactory.getLogger(JustificationSystem.class);
    protected boolean versioningEnable=false;

    public JustificationSystem() throws VerificationException, WrongEvidenceException {
        justificationDiagram =new JustificationDiagram();

    }
    public JustificationSystem(PatternsBase patternsBase, List<SupportRole> baseEvidences) throws VerificationException, WrongEvidenceException {
        this();
        this.patternsBase = patternsBase;
        this.baseEvidences = baseEvidences;
    }

    @Override
    public Pattern getPattern(String patternId) {
        return patternsBase.getPattern(patternId);
    }

    @Override
    public void addPattern(Pattern pattern) {
        patternsBase.addPattern(pattern);
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

    private void setJustificationDiagram(JustificationDiagram justificationDiagram) {
        this.justificationDiagram=justificationDiagram;
    }

    @Override
    @XmlElement
    public OutputType getObjective() {
        return objective;
    }

    @Override
    public boolean validate() {
        return patternsBase.getConstraints().stream().allMatch(constraint -> constraint.verify(justificationDiagram.getSteps()));
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
    public JustificationStep constructStep(Pattern pattern, List<SupportRole> evidences, Conclusion conclusion) throws StepBuildingException, WrongEvidenceException, StrategyException {
        if(evidences==null || evidences.isEmpty()){
            throw new StepBuildingException("Need evidences");
        }
        if(conclusion==null){
            throw new StepBuildingException("Need a conclusion");
        }
        if(pattern==null){
            throw new StepBuildingException("Need a pattern");
        }

        try{
            List<SupportRole> usefulEvidences=filterUsefulSupports(pattern,evidences,conclusion);
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

    protected void postStepCreated(JustificationStep step) throws CloneNotSupportedException, WrongEvidenceException {
        InputType<? extends Conclusion> evidenceRoleType=new InputType<>("",step.getConclusion().getClass());

        if(autoSupportFillEnable){
            baseEvidences.add(evidenceRoleType.create(step.getConclusion().clone()));
            List<SupportRole> evidencesToAdd=step.getSupports().stream().filter(supportRole -> supportRole.getSupport().isPrimitiveInputType() && !baseEvidences.contains(supportRole)).collect(Collectors.toList());
            baseEvidences.addAll(evidencesToAdd);
        }
    }

    protected List<SupportRole> filterUsefulSupports(Pattern pattern, List<SupportRole> supports, Conclusion conclusion) throws StepBuildingException {
        List<SupportRole> usefulEvidences=pattern.filterUsefulEvidences(supports);
        if(autoSupportFillEnable && usefulEvidences.size()!=pattern.getInputTypes().size()){
            LOGGER.info("Missing supports. Trying to autofill");
            List<InputType> inputTypeList=pattern.filterNotFillInput(usefulEvidences);
            LOGGER.info("Found "+inputTypeList+ " to fill");
            List<SupportRole> autoFillSupports=collectSupports(inputTypeList);
            LOGGER.info("Found "+autoFillSupports+ ". Add them to supports");
            usefulEvidences.addAll(autoFillSupports);
        }
        if(versioningEnable){
            usefulEvidences=usefulEvidences.stream().filter(supportRole -> supportRole.getSupport().getElement().getVersion()==null || supportRole.getSupport().getElement().getVersion().equals(conclusion.getElement().getVersion())).collect(Collectors.toList());
        }
        return usefulEvidences;
    }

    private List<SupportRole> collectSupports(List<InputType> inputTypes) throws StepBuildingException {
        List<SupportRole> collected=new ArrayList<>();
        for(InputType inputType:inputTypes){
            for(SupportRole supportRole:baseEvidences){
                if(inputType.getType().equals(supportRole.getSupport().getClass())){
                    if(!collected.contains(supportRole)){
                        collected.add(supportRole);
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
    public void resolveHypothesis(JustificationStep step, Hypothesis hypothesis, Support support) throws WrongEvidenceException, PatternConstraintException {
        SupportRole hypo=step.getSupports().stream().filter(evidenceRole -> evidenceRole.getSupport() instanceof Hypothesis && evidenceRole.getSupport().equals(hypothesis)).collect(singletonCollector());
        Pattern pattern=patternsBase.getPattern(step.getPatternId());
        Optional<InputType> evidenceRoleTypeResult=pattern.getInputTypes().stream().filter(evidenceRoleType -> evidenceRoleType.check(hypo.getSupport())).findAny();
        if(evidenceRoleTypeResult.isPresent()){
            SupportRole res=evidenceRoleTypeResult.get().create(support);
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
    public void removeSteps() {
        justificationDiagram.getSteps().clear();
    }

    @Override
    public String toString() {
        return "ArgumentationSystem{" +
                "patternsBase=" + patternsBase +
                ", objective=" + objective +
                ", baseEvidences=" + baseEvidences +
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
