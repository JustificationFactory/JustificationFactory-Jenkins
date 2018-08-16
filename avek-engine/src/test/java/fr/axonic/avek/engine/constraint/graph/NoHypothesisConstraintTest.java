package fr.axonic.avek.engine.constraint.graph;

import fr.axonic.avek.engine.pattern.JustificationStep;
import fr.axonic.avek.engine.constraint.JustificationSystemConstraint;
import fr.axonic.avek.engine.constraint.PatternConstraintTest;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.evidence.Hypothesis;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 14/03/17.
 */
public class NoHypothesisConstraintTest extends PatternConstraintTest{

    @Test
    public void testNoHypothesis(){
        JustificationSystemConstraint justificationSystemConstraint =new NoHypothesisConstraint();
        assertTrue(justificationSystemConstraint.verify(argumentationSystem.getJustificationDiagram().getSteps()));
    }

    @Test
    public void testWithHypothesis(){
        JustificationSystemConstraint justificationSystemConstraint =new NoHypothesisConstraint();

        List<JustificationStep> stepList=argumentationSystem.getJustificationDiagram().getSteps();
        JustificationStep hyp=stepList.get(0);
        List<Support> supportRoles =hyp.getSupports();
        supportRoles.set(0, new Hypothesis());
        hyp.setSupports(supportRoles);
        stepList.set(0,hyp);

        assertFalse(justificationSystemConstraint.verify(stepList));
    }



}