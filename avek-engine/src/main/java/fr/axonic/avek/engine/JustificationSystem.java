package fr.axonic.avek.engine;

import fr.axonic.avek.engine.diagram.JustificationDiagram;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.*;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.constraint.PatternConstraintException;
import fr.axonic.avek.engine.constraint.graph.NoCycleConstraint;
import fr.axonic.avek.engine.support.evidence.Hypothesis;
import fr.axonic.avek.engine.support.Support;
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
public class JustificationSystem<T extends PatternsBase> implements JustificationSystemAPI<T> {

    protected boolean autoSupportFillEnable =false;
    protected T patternsBase;
    protected List<Support> registeredEvidences = new ArrayList<>();
    protected JustificationDiagram justificationDiagram;
    //@XmlTransient
    private final static Logger LOGGER = LoggerFactory.getLogger(JustificationSystem.class);
    protected boolean versioningEnable=false;

    public JustificationSystem() {
        justificationDiagram =new JustificationDiagram();

    }
    public JustificationSystem(T patternsBase, List<Support> registeredEvidences) throws VerificationException, WrongEvidenceException {
        this();
        this.patternsBase = patternsBase;
        this.registeredEvidences = registeredEvidences;
    }

    @Override
    @XmlElement
    @XmlElementWrapper
    public List<Support> getRegisteredEvidences() {
        return registeredEvidences;
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


    private void setRegisteredEvidences(List<Support> registeredEvidences) {
        this.registeredEvidences = registeredEvidences;
    }

    private void setJustificationDiagram(JustificationDiagram justificationDiagram) {
        this.justificationDiagram=justificationDiagram;
    }

    @Override
    public boolean validate() {
        if(patternsBase instanceof ListPatternsBase){
            return ((ListPatternsBase)patternsBase).getConstraints().stream().allMatch(constraint -> constraint.verify(justificationDiagram.getSteps()));

        }
        return true;
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

    protected void postStepCreated(JustificationStep step) throws CloneNotSupportedException, WrongEvidenceException {
        InputType<? extends Conclusion> evidenceRoleType=new InputType<>("",step.getConclusion().getClass());

        if(autoSupportFillEnable){
            registeredEvidences.add(step.getConclusion().clone());
            List<Support> evidencesToAdd=step.getSupports().stream().filter(supportRole -> supportRole.isPrimitiveInputType() && !registeredEvidences.contains(supportRole)).collect(Collectors.toList());
            registeredEvidences.addAll(evidencesToAdd);
        }
    }

    protected List<Support> filterUsefulSupports(Pattern pattern, List<Support> supports, Conclusion conclusion) throws StepBuildingException {
        List<Support> usefulEvidences=pattern.filterUsefulEvidences(supports);
        if(autoSupportFillEnable && usefulEvidences.size()!=pattern.getInputTypes().size()){
            LOGGER.info("Missing supports. Trying to autofill");
            List<InputType> inputTypeList=pattern.filterNotFillInput(usefulEvidences);
            LOGGER.info("Found "+inputTypeList+ " to fill");
            List<Support> autoFillSupports=collectSupports(inputTypeList);
            LOGGER.info("Found "+autoFillSupports+ ". Add them to supports");
            usefulEvidences.addAll(autoFillSupports);
        }
        if(versioningEnable){
            usefulEvidences=usefulEvidences.stream().filter(supportRole -> supportRole.getElement().getVersion()==null || supportRole.getElement().getVersion().equals(conclusion.getElement().getVersion())).collect(Collectors.toList());
        }
        return usefulEvidences;
    }

    private List<Support> collectSupports(List<InputType> inputTypes) throws StepBuildingException {
        List<Support> collected=new ArrayList<>();
        for(InputType inputType:inputTypes){
            for(Support support: registeredEvidences){
                if(inputType.getType().equals(support.getClass())){
                    if(!collected.contains(support)){
                        collected.add(support);
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
    public String toString() {
        return "ArgumentationSystem{" +
                "patternsBase=" + patternsBase +
                ", registeredEvidences=" + registeredEvidences +
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
