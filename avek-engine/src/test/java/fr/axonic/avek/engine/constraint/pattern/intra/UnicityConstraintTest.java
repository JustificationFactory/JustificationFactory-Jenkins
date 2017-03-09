package fr.axonic.avek.engine.constraint.pattern.intra;

import fr.axonic.avek.engine.constraint.PatternConstraint;
import fr.axonic.avek.engine.constraint.pattern.PatternConstraintTest;
import fr.axonic.avek.engine.constraint.pattern.intra.UnicityConstraint;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 09/03/17.
 */
public class UnicityConstraintTest extends PatternConstraintTest {

    @Test
    public void verifyUnicity() throws Exception {
        PatternConstraint patternConstraint=new UnicityConstraint(pattern);
        assertTrue(patternConstraint.verify(argumentationSystem.getSteps()));
    }
    @Test
    public void verifyNotUnicity() throws Exception {
        PatternConstraint patternConstraint=new UnicityConstraint(pattern);
        argumentationSystem.constructStep(pattern, Arrays.asList(new EvidenceRole[] {evStimulation0,evSubject0}), experimentation0);
        assertFalse(patternConstraint.verify(argumentationSystem.getSteps()));
    }

}