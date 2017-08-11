package fr.axonic.avek.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jaxrs.Jaxrs2TypesModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.engine.strategy.HumanStrategy;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.instance.conclusion.ExperimentationConclusion;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by cduffau on 11/08/17.
 */

public class JacksonSerialization {

    @Test
    public void testStep() throws IOException {
        Conclusion conclusion = new ExperimentationConclusion();
        Step stepToCreate = new Step("1", new HumanStrategy(), new ArrayList<SupportRole>(), conclusion);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(stepToCreate);
        System.out.println(json);
        mapper.readValue(json, Step.class);

    }
}
