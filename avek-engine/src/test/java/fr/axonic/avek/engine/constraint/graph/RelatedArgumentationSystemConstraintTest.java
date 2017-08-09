package fr.axonic.avek.engine.constraint.graph;

import fr.axonic.avek.engine.constraint.ArgumentationSystemConstraint;
import fr.axonic.avek.engine.constraint.PatternConstraintTest;
import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.avek.engine.strategy.HumanStrategy;
import fr.axonic.avek.engine.strategy.Strategy;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.instance.conclusion.Effect;
import fr.axonic.avek.instance.evidence.Stimulation;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 21/03/17.
 */
public class RelatedArgumentationSystemConstraintTest extends PatternConstraintTest{

    @Test
    public void verifyConnexeWithOneStep() throws Exception {
        ArgumentationSystemConstraint argumentationSystemConstraint =new RelatedArgumentationSystemConstraint();
        assertTrue(argumentationSystemConstraint.verify(argumentationSystem.getSteps()));
    }

    @Test
    public void verifyConnexeWithMoreThanOneStep() throws Exception {
        Strategy strategy=new HumanStrategy();
        InputType evidenceRoleType1=new InputType("Test1", Evidence.class);
        Evidence evidence1=new Evidence("evidence1", new Stimulation());
        SupportRole supportRole1 =evidenceRoleType1.create(evidence1);

        InputType evidenceRoleType2=new InputType("Test2", Evidence.class);
        Evidence evidence2=new Evidence("evidence2", new Stimulation());
        SupportRole supportRole2 =evidenceRoleType2.create(evidence2);
        Conclusion conclusion=new Conclusion("conclusion1",new Effect());
        Conclusion conclusion2=new Conclusion("conclusion2",new Effect());
        Conclusion conclusion3=new Conclusion("conclusion3",new Effect());

        InputType conclusionRoleType2=new InputType("Test3", Conclusion.class);
        SupportRole conclusionRole1=conclusionRoleType2.create(conclusion);
        InputType conclusionRoleType3=new InputType("Test4", Conclusion.class);
        SupportRole conclusionRole2=conclusionRoleType3.create(conclusion2);


        Step step1=new Step("1",strategy,Arrays.asList(supportRole1, supportRole2), conclusion);
        Step step2=new Step("1",strategy,Arrays.asList(supportRole1, supportRole2, conclusionRole1), conclusion3);
        Step step3=new Step("1",strategy,Arrays.asList(supportRole1, supportRole2, conclusionRole1), conclusion2);
        ArgumentationSystemConstraint argumentationSystemConstraint =new RelatedArgumentationSystemConstraint();
        assertTrue(argumentationSystemConstraint.verify(Arrays.asList(step1,step2,step3)));
    }

    @Test
    public void verifyNotConnexe() throws Exception {
        Strategy strategy=new HumanStrategy();
        InputType evidenceRoleType1=new InputType("Test1", Evidence.class);
        Evidence evidence1=new Evidence("evidence1", new Stimulation());
        SupportRole supportRole1 =evidenceRoleType1.create(evidence1);

        InputType evidenceRoleType2=new InputType("Test2", Evidence.class);
        Evidence evidence2=new Evidence("evidence2", new Stimulation());
        SupportRole supportRole2 =evidenceRoleType2.create(evidence2);
        Conclusion conclusion=new Conclusion("conclusion1",new Effect());
        Conclusion conclusion2=new Conclusion("conclusion2",new Effect());
        Conclusion conclusion3=new Conclusion("conclusion3",new Effect());

        InputType conclusionRoleType2=new InputType("Test3", Conclusion.class);
        SupportRole conclusionRole1=conclusionRoleType2.create(conclusion);
        InputType conclusionRoleType3=new InputType("Test4", Conclusion.class);
        SupportRole conclusionRole2=conclusionRoleType3.create(conclusion2);


        Step step1=new Step("1",strategy,Arrays.asList(supportRole1, supportRole2), conclusion);
        Step step2=new Step("1",strategy,Arrays.asList(supportRole1, supportRole2, conclusionRole1), conclusion3);
        Step step3=new Step("1",strategy,Arrays.asList(supportRole1, supportRole2), conclusion2);
        ArgumentationSystemConstraint argumentationSystemConstraint =new RelatedArgumentationSystemConstraint();
        assertFalse(argumentationSystemConstraint.verify(Arrays.asList(step1,step2,step3)));
    }
}