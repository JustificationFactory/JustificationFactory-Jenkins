package fr.axonic.avek.engine.pattern.type;

import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.instance.conclusion.Experimentation;
import fr.axonic.avek.instance.conclusion.ExperimentationConclusion;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 07/09/17.
 */
public class OutputTypeTest {

    @Test
    public void testCreate() throws StepBuildingException {
        OutputType<ExperimentationConclusion> outputType=new OutputType<>(ExperimentationConclusion.class);
        Conclusion conclusion=outputType.create("",new Experimentation());
        assertNotNull(conclusion);
    }

}