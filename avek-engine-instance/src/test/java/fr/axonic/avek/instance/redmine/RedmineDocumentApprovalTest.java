package fr.axonic.avek.instance.redmine;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import fr.axonic.avek.engine.support.evidence.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class RedmineDocumentApprovalTest {

    @Ignore
    @Test
    public void rootShouldHaveTypeField() throws IOException {
        RedmineDocumentApproval approval1 = new RedmineDocumentApproval("DOC1", new Document("DOC1"));
        approval1.getElement().setVersion("A");

        RedmineDocumentApproval approval2 = new RedmineDocumentApproval("DOC2", new Document("DOC2"));
        approval2.getElement().setVersion("B");

        ObjectMapper mapper = new ObjectMapper();

        AnnotationIntrospector aiJaxb = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
        AnnotationIntrospector aiJackson = new JacksonAnnotationIntrospector();

        mapper.setAnnotationIntrospector(AnnotationIntrospector.pair(aiJaxb, aiJackson));

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        List<RedmineDocumentApproval> approvals = Arrays.asList(approval1, approval2);
        mapper.writeValue(os, approvals);

        JSONArray output = new JSONArray(new String(os.toByteArray()));

        assertTrue(output.getJSONObject(0).has("@type"));
        assertTrue(output.getJSONObject(1).has("@type"));
    }
}