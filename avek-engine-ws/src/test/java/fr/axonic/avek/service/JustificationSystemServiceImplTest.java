package fr.axonic.avek.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import fr.axonic.avek.dao.JerseyMapperProvider;
import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.JustificationSystemAPI;
import fr.axonic.avek.engine.StepToCreate;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.Pattern;
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
import static org.junit.Assert.assertNotNull;

/**
 * Created by cduffau on 17/03/17.
 */
public class JustificationSystemServiceImplTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(JustificationSystemServiceImpl.class);
    }

    @Test
    public void testGetArgumentationSystems(){
        Response argumentationSystem=target("/justification/systems").request().get();
        assertNotNull(argumentationSystem);
        assertEquals(argumentationSystem.getStatusInfo(), Response.Status.OK);
        List systems=argumentationSystem.readEntity(List.class);
        assertNotNull(systems);
        assertFalse(systems.isEmpty());
    }

    @Test
    public void testGetArgumentationSystem(){
        Response argumentationSystemResponse=target("/justification/CLINICAL_STUDIES").request().get();
        //System.out.println(argumentationSystem);
        assertNotNull(argumentationSystemResponse);
        assertEquals(argumentationSystemResponse.getStatusInfo(), Response.Status.OK);
        JustificationSystem justificationSystem =argumentationSystemResponse.readEntity(JustificationSystem.class);
        assertNotNull(justificationSystem);
    }


    @Test
    public void testRegisterArgumentationSystem() throws VerificationException, WrongEvidenceException {
        JustificationSystemAPI argumentationSystem=new JustificationSystem();
        Response argSystem=target("/justification/system").request().post(Entity.json(argumentationSystem));
        assertNotNull(argSystem);
        assertEquals(argSystem.getStatusInfo(), Response.Status.ACCEPTED);
        String system=argSystem.readEntity(String.class);
        assertNotNull(system);
    }


}