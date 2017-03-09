package fr.axonic.avek.engine;

import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.constraint.PatternConstraint;
import fr.axonic.avek.engine.evidence.Evidence;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.avek.engine.instance.conclusion.EstablishedEffect;
import fr.axonic.avek.engine.instance.conclusion.Experimentation;
import fr.axonic.avek.engine.instance.evidence.*;
import fr.axonic.avek.engine.instance.strategy.*;
import fr.axonic.avek.engine.strategy.Actor;
import fr.axonic.avek.engine.strategy.Rationale;
import fr.axonic.avek.engine.strategy.Role;
import fr.axonic.avek.engine.strategy.Strategy;
import fr.axonic.validation.exception.VerificationException;
import org.apache.log4j.Logger;
import org.eclipse.persistence.jaxb.MarshallerProperties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by cduffau on 04/08/16.
 */
@XmlRootElement
public class ArgumentationSystem implements ArgumentationSystemAPI {

    private PatternsBase patternsBase;
    private ConclusionType objective;
    private List<EvidenceRole> baseEvidences = new ArrayList<>();
    private List<Step> steps;
    private final static Logger LOGGER = Logger.getLogger(ArgumentationSystem.class);

    public ArgumentationSystem() throws VerificationException, WrongEvidenceException {
        steps=new ArrayList<>();

    }
    public ArgumentationSystem(PatternsBase patternsBase, List<EvidenceRole> baseEvidences) throws VerificationException, WrongEvidenceException {
        this();
        this.patternsBase=patternsBase;
        this.baseEvidences=baseEvidences;
    }



    @Override
    public Pattern getPattern(String patternId) {
        return patternsBase.getPatterns().stream().filter(pattern -> pattern.getId().equals(patternId)).collect(singletonCollector());
    }

    @Override
    @XmlElement
    @XmlElementWrapper
    public List<EvidenceRole> getBaseEvidences() {
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


    private void setBaseEvidences(List<EvidenceRole> baseEvidences) {
        this.baseEvidences = baseEvidences;
    }

    private void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    @XmlElement
    public ConclusionType getObjective() {
        return objective;
    }

    @Override
    public boolean validate() {
        for(PatternConstraint constraint : patternsBase.getConstraints()){
            if(!constraint.verify(steps)){
                return false;
            }
        }
        return true;
    }

    public void setObjective(ConclusionType objective) throws WrongObjectiveException {
        if(patternsBase.getPatterns().stream().filter(pattern -> pattern.getConclusionType().equals(objective)).count()>=1){
            this.objective = objective;
        }
        else{
            throw new WrongObjectiveException();
        }
    }

    @Override
    public Step constructStep(Pattern pattern, List<EvidenceRole> evidences, Conclusion conclusion) throws StepBuildingException, WrongEvidenceException, StrategyException {
        try{
            List<EvidenceRole> usefullEvidences=pattern.filterUsefullEvidences(evidences);
            Step step=pattern.createStep(usefullEvidences,conclusion.clone(), new Actor("Chloé", Role.INTERMEDIATE_EXPERT));
            steps.add(step);
            LOGGER.info(step.getConclusion());
            EvidenceRoleType evidenceRoleType=new EvidenceRoleType("",step.getConclusion().getClass());
            baseEvidences.add(evidenceRoleType.create(step.getConclusion().clone()));
            return step;
        }
        catch (NullPointerException  e){
            throw new StepBuildingException("Unknown pattern");
        }


    }

    @Override
    public List<Step> getSteps() {
        return steps;
    }

    private static void save(Object object, File file) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(new Class[]{object.getClass()});
        Marshaller m = context.createMarshaller();
        m.setProperty(MarshallerProperties.MEDIA_TYPE,
                "application/json");
        m.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
        m.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, Boolean.valueOf(false));
        FileOutputStream fos = new FileOutputStream(file);
        m.marshal(object, fos);
        fos.close();


    }


    private static <T> Collector<T, ?, T> singletonCollector() {
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
