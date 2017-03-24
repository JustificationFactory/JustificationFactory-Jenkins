package fr.axonic.avek.service;


import fr.axonic.avek.engine.ArgumentationSystem;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 17/03/17.
 */
public class ArgumentationSystemServiceImplTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(ArgumentationSystemServiceImpl.class);
    }

    @Test
    public void testGetArgumentation(){
        Response argumentationSystem=target("/argumentation/AXONIC").request().get(Response.class);
        System.out.println(argumentationSystem);
        assertNotNull(argumentationSystem);
        assertEquals(argumentationSystem.getStatusInfo(), Response.Status.OK);
        //assertTrue(argumentationSystem.getPatternsBase().getPatterns().size()>0);
    }

}