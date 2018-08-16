package fr.axonic.avek.engine.constraint.step;

import fr.axonic.avek.engine.constraint.JustificationSystemConstraint;
import fr.axonic.avek.engine.constraint.PatternConstraintTest;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 09/03/17.
 */
public class UniquenessConstraintTest extends PatternConstraintTest {

    @Test
    public void verifyUnicity() throws Exception {
        JustificationSystemConstraint justificationSystemConstraint =new UniquenessConstraint(pattern);
        assertTrue(justificationSystemConstraint.verify(argumentationSystem.getJustificationDiagram().getSteps()));
    }
    @Test
    public void verifyNotUnicity() throws Exception {
        JustificationSystemConstraint justificationSystemConstraint =new UniquenessConstraint(pattern);
        argumentationSystem.constructStep(pattern, Arrays.asList(evStimulation0,evSubject0,evActor0), experimentation0);
        assertFalse(justificationSystemConstraint.verify(argumentationSystem.getJustificationDiagram().getSteps()));
    }

}