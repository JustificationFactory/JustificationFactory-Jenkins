package fr.axonic.avek.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.axonic.avek.engine.ArgumentationSystem;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.engine.strategy.HumanStrategy;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.instance.conclusion.ExperimentationConclusion;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.ArrayList;
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
    public void testPostArgumentationStep() {

        Conclusion conclusion = new ExperimentationConclusion();
        Step stepToCreate = new Step("1", new HumanStrategy(), new ArrayList<SupportRole>(), conclusion);
         Response stepResponse=target("/argumentation/AXONIC/1/step").request().post(Entity.json(stepToCreate));
         assertNotNull(stepResponse);
         System.out.println(stepResponse);
         assertEquals(stepResponse.getStatusInfo(),Response.Status.OK );
         Step step=stepResponse.readEntity(Step.class);
         assertNotNull(step);
         assertEquals(step.getConclusion(),conclusion);

    }

}