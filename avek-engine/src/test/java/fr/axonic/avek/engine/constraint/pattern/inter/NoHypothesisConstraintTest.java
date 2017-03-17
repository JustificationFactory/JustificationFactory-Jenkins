package fr.axonic.avek.engine.constraint.pattern.inter;

import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.engine.constraint.PatternConstraint;
import fr.axonic.avek.engine.constraint.pattern.PatternConstraintTest;
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
        PatternConstraint patternConstraint=new NoHypothesisConstraint();
        assertTrue(patternConstraint.verify(argumentationSystem.getSteps()));
    }

    @Test
    public void testWithHypothesis(){
        PatternConstraint patternConstraint=new NoHypothesisConstraint();

        List<Step> stepList=argumentationSystem.getSteps();
        Step hyp=stepList.get(0);
        List<SupportRole> supportRoles =hyp.getEvidences();
        SupportRole supportRole = supportRoles.get(0);
        supportRole.setSupport(new Hypothesis());
        supportRoles.set(0, supportRole);
        hyp.setEvidences(supportRoles);
        stepList.set(0,hyp);

        assertFalse(patternConstraint.verify(stepList));
    }



}