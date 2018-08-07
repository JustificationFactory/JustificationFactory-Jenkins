package fr.axonic.avek.bus;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.axonic.avek.dao.JerseyMapperProvider;
import fr.axonic.avek.engine.support.evidence.Document;
import fr.axonic.avek.instance.redmine.RedmineDocumentApproval;
import fr.axonic.avek.instance.redmine.RedmineDocumentEvidence;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import java.util.Arrays;

import static org.junit.Assert.*;

public class JustificationBusServiceImplementationTest extends JerseyTest {

    protected Application configure() {
        return new ResourceConfig(JustificationBusServiceImplementation.class);
    }

    @Test
    public void shouldAcceptRedmineEvidences() throws JsonProcessingException {
        TransmittedSupports supports = new TransmittedSupports();
        supports.setSupports(Arrays.asList(
                new RedmineDocumentEvidence("DOC", new Document("DOC")),
                new RedmineDocumentApproval("DOC_APPROVAL", new Document("DOC_APPROVAL"))));

        Response ok = target("/bus/supports").request().post(Entity.json(supports));

        assertNotNull(ok);
        assertEquals(200, ok.getStatus());
    }
}