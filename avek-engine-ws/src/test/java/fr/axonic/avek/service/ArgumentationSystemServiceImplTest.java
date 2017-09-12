package fr.axonic.avek.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import fr.axonic.avek.dao.JerseyMapperProvider;
import fr.axonic.avek.engine.ArgumentationSystem;
import fr.axonic.avek.engine.StepToCreate;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.avek.engine.strategy.Actor;
import fr.axonic.avek.engine.strategy.Role;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.instance.avek.conclusion.ExperimentationConclusion;
import fr.axonic.avek.instance.avek.evidence.Stimulation;
import fr.axonic.avek.instance.avek.evidence.StimulationEvidence;
import fr.axonic.avek.instance.avek.evidence.Subject;
import fr.axonic.avek.instance.avek.evidence.SubjectEvidence;
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
import static org.junit.Assert.assertNotNull;

/**
 * Created by cduffau on 17/03/17.
 */
public class ArgumentationSystemServiceImplTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(ArgumentationSystemServiceImpl.class);
    }

    @Test
    public void testGetArgumentationSystems(){
        Response argumentationSystem=target("/argumentation/systems").request().get();
        assertNotNull(argumentationSystem);
        assertEquals(argumentationSystem.getStatusInfo(), Response.Status.OK);
        List systems=argumentationSystem.readEntity(List.class);
        assertNotNull(systems);
        assertFalse(systems.isEmpty());
    }

    @Test
    public void testGetArgumentationSystem(){
        Response argumentationSystemResponse=target("/argumentation/AXONIC").request().get();
        //System.out.println(argumentationSystem);
        assertNotNull(argumentationSystemResponse);
        assertEquals(argumentationSystemResponse.getStatusInfo(), Response.Status.OK);
        ArgumentationSystem argumentationSystem=argumentationSystemResponse.readEntity(ArgumentationSystem.class);
        assertNotNull(argumentationSystem);
    }

    @Test
    public void testGetArgumentationSystemPatterns(){
        Response argumentationSystem=target("/argumentation/AXONIC/patterns").request().get();
        assertNotNull(argumentationSystem);
        assertEquals(argumentationSystem.getStatusInfo(), Response.Status.OK);
        List patterns=argumentationSystem.readEntity(List.class);
        assertNotNull(patterns);
        assertFalse(patterns.isEmpty());

    }

    @Test
    public void testGetArgumentationSystemPattern(){
        Response argumentationSystem=target("/argumentation/AXONIC/patterns/1").request().get();
        //System.out.println(argumentationSystem);
        assertNotNull(argumentationSystem);
        assertEquals(argumentationSystem.getStatusInfo(), Response.Status.OK);
        Pattern pattern=argumentationSystem.readEntity(Pattern.class);
        assertNotNull(pattern);
        assertEquals(pattern.getId(),"1");
    }
    @Test
    public void testPostWrongArgumentationStep() throws JsonProcessingException {
        Conclusion conclusion = new ExperimentationConclusion();
        StepToCreate stepToCreate = new StepToCreate(new ArrayList<>(),conclusion);
        Response stepResponse=target("/argumentation/AXONIC/1/step").request().post(Entity.json(stepToCreate));
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
        SupportRole evStimulation0 = rtStimulation.create(stimulation0 );
        SupportRole evSubject0 = rtSubject.create(subject0);
        SupportRole evActor0=rtActor.create(actor0);
        StepToCreate stepToCreate=new StepToCreate(Arrays.asList(evActor0,evSubject0,evStimulation0),experimentation0);
        System.out.println(new JerseyMapperProvider().getContext(null).writeValueAsString(stepToCreate));
        Response stepResponse=target("/argumentation/AXONIC/1/step").request().post(Entity.json(stepToCreate));
        assertNotNull(stepResponse);
        assertEquals(stepResponse.getStatusInfo(),Response.Status.CREATED );
        Step step=stepResponse.readEntity(Step.class);
        assertNotNull(step);
        //assertEquals(step.getConclusion(),experimentation0);

    }

}