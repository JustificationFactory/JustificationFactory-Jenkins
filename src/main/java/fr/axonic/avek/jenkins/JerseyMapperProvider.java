package fr.axonic.avek.jenkins;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

public class JerseyMapperProvider {
    private static ObjectMapper apiMapper;

    static {
        apiMapper=new ObjectMapper();
        AnnotationIntrospector aiJaxb = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
        AnnotationIntrospector aiJackson = new JacksonAnnotationIntrospector();
        // first Jaxb, second Jackson annotations
        apiMapper.setAnnotationIntrospector(AnnotationIntrospector.pair(aiJaxb, aiJackson));
    }

    public ObjectMapper getContext()
    {
        return apiMapper;
    }
}