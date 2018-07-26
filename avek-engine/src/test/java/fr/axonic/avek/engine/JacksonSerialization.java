package fr.axonic.avek.engine;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.JustificationStep;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.avek.engine.strategy.Actor;
import fr.axonic.avek.engine.strategy.Role;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.instance.MockedArgumentationSystem;
import fr.axonic.avek.instance.avek.conclusion.ExperimentationConclusion;
import fr.axonic.avek.instance.avek.evidence.Stimulation;
import fr.axonic.avek.instance.avek.evidence.StimulationEvidence;
import fr.axonic.avek.instance.avek.evidence.Subject;
import fr.axonic.avek.instance.avek.evidence.SubjectEvidence;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by cduffau on 11/08/17.
 */

public class JacksonSerialization {
    private ObjectMapper mapper;
    @Before
    public void setup(){
        mapper = new ObjectMapper();
        AnnotationIntrospector aiJaxb = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
        AnnotationIntrospector aiJackson = new JacksonAnnotationIntrospector();
        // first Jaxb, second Jackson annotations
        mapper.setAnnotationIntrospector(AnnotationIntrospector.pair(aiJaxb, aiJackson));

    }

    @Test
    public void testPattern() throws IOException, VerificationException, WrongEvidenceException {
        Pattern pattern= MockedArgumentationSystem.getAXONICArgumentationSystem().getPattern("1");
        String json = mapper.writeValueAsString(pattern);
        System.out.println(json);
        Pattern p=mapper.readValue(json, Pattern.class);
        assertEquals(pattern,p);
    }

    @Test
    public void testStep() throws IOException, WrongEvidenceException, VerificationException, StrategyException, StepBuildingException {
        Pattern pattern= MockedArgumentationSystem.getAXONICArgumentationSystem().getPattern("1");
        JustificationSystemAPI argumentationSystem=MockedArgumentationSystem.getAXONICArgumentationSystem();
        StimulationEvidence stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
        SubjectEvidence subject0 = new SubjectEvidence("Subject 0",new Subject());
        Actor actor0=new Actor("Chloé", Role.SENIOR_EXPERT);
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0",subject0.getElement(),stimulation0.getElement());
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        InputType<Actor> rtActor=new InputType<>("actor", Actor.class);
        SupportRole evStimulation0 = rtStimulation.create(stimulation0 );
        SupportRole evSubject0 = rtSubject.create(subject0);
        SupportRole evActor0=rtActor.create(actor0);
        JustificationStep stepToCreate = argumentationSystem.constructStep(pattern, Arrays.asList(evStimulation0,evSubject0,evActor0), experimentation0);

        String json = mapper.writeValueAsString(stepToCreate);
        JustificationStep step=mapper.readValue(json, JustificationStep.class);
    }

    @Test
    public void testSupports() throws IOException, VerificationException, WrongEvidenceException {
        List<SupportRole> supports=MockedArgumentationSystem.getAXONICArgumentationSystem().getBaseEvidences();
        String json = mapper.writeValueAsString(supports);
        System.out.println(json);
        /**List<SupportRole> roles=mapper.readValue(json, new TypeReference<Set<SupportRole>>() {});
        System.out.println(roles);
        assertEquals(supports,roles);*/
    }
}
