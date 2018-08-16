package fr.axonic.avek.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.axonic.avek.dao.JerseyMapperProvider;
import fr.axonic.avek.engine.StepToCreate;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.JustificationStep;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.avek.engine.strategy.Actor;
import fr.axonic.avek.engine.strategy.Role;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.instance.DocumentEvidence;
import fr.axonic.avek.instance.clinical.conclusion.ExperimentationConclusion;
import fr.axonic.avek.instance.clinical.evidence.Stimulation;
import fr.axonic.avek.instance.clinical.evidence.StimulationEvidence;
import fr.axonic.avek.instance.clinical.evidence.Subject;
import fr.axonic.avek.instance.clinical.evidence.SubjectEvidence;
import fr.axonic.validation.exception.VerificationException;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class JustificationDiagramServiceImplTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(JustificationDiagramServiceImpl.class);
    }

    @Test
    public void testPostWrongArgumentationStep() throws JsonProcessingException {
        Conclusion conclusion = new ExperimentationConclusion();
        StepToCreate stepToCreate = new StepToCreate(new ArrayList<>(),conclusion);
        Response stepResponse=target("/justification/CLINICAL_STUDIES/1/step").request().post(Entity.json(stepToCreate));
        assertNotNull(stepResponse);
        assertEquals(stepResponse.getStatusInfo(),Response.Status.EXPECTATION_FAILED);
        List error=stepResponse.readEntity(List.class);
        assertNotNull(error);

    }

    @Test
    public void testPostRightArgumentationStep() throws JsonProcessingException, VerificationException, WrongEvidenceException {

        StimulationEvidence stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
        SubjectEvidence subject0 = new SubjectEvidence("Subject 0",new Subject());
        Actor actor0=new Actor("Chloé", Role.SENIOR_EXPERT);
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0",subject0.getElement(),stimulation0.getElement());
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        InputType<Actor> rtActor=new InputType<>("actor", Actor.class);
        Support evStimulation0 = rtStimulation.create(stimulation0 );
        Support evSubject0 = rtSubject.create(subject0);
        Support evActor0=rtActor.create(actor0);
        StepToCreate stepToCreate=new StepToCreate(Arrays.asList(evActor0,evSubject0,evStimulation0),experimentation0);
        System.out.println(new JerseyMapperProvider().getContext(null).writeValueAsString(stepToCreate));
        Response stepResponse=target("/justification/CLINICAL_STUDIES/1/step").request().post(Entity.json(stepToCreate));
        assertNotNull(stepResponse);
        assertEquals(stepResponse.getStatusInfo(),Response.Status.CREATED );
        JustificationStep step=stepResponse.readEntity(JustificationStep.class);
        assertNotNull(step);
        //assertEquals(step.getConclusion(),experimentation0);

    }

    @Test
    public void testGetTypeContent() {
        Response fields=target("/justification/type").queryParam("type", DocumentEvidence.class.getName()).request().get();
        assertNotNull(fields);
        assertEquals(fields.getStatusInfo(), Response.Status.OK);
        String system=fields.readEntity(String.class);
        assertNotNull(system);
    }

}