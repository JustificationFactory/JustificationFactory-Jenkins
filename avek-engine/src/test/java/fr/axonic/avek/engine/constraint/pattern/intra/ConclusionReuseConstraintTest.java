package fr.axonic.avek.engine.constraint.pattern.intra;

import fr.axonic.avek.engine.EvidenceRoleType;
import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.constraint.PatternConstraint;
import fr.axonic.avek.engine.constraint.pattern.PatternConstraintTest;
import fr.axonic.avek.engine.constraint.pattern.intra.ConclusionReuseConstraint;
import fr.axonic.avek.engine.evidence.Evidence;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.avek.engine.instance.conclusion.Effect;
import fr.axonic.avek.engine.instance.conclusion.ExperimentationConclusion;
import fr.axonic.avek.engine.instance.evidence.Result;
import fr.axonic.base.engine.AList;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 09/03/17.
 */
public class ConclusionReuseConstraintTest extends PatternConstraintTest {

    @Test
    public void verifyConclusionReuse() throws Exception {
        PatternConstraint patternConstraint=new ConclusionReuseConstraint(pattern);

        EvidenceRoleType rtExperimentation = new EvidenceRoleType("experimentation", ExperimentationConclusion.class);
        EvidenceRoleType rtResults = new EvidenceRoleType("result", Evidence.class);

        Evidence<Result> results0 = new Evidence<Result>("Result 0",new Result(new AList<>()));
        Conclusion<Effect> effect0 = new Conclusion<Effect>("Effect 0",new Effect());
        EvidenceRole evExperimentation0 = rtExperimentation.create(experimentation0);
        EvidenceRole evResults0 = rtResults.create(results0);
        argumentationSystem.constructStep(argumentationSystem.getPattern("2"), Arrays.asList(new EvidenceRole[] {evExperimentation0,evResults0}), effect0);
        assertTrue(patternConstraint.verify(argumentationSystem.getSteps()));
    }

    @Test
    public void verifyConclusionNotReuse() throws Exception {
        PatternConstraint patternConstraint=new ConclusionReuseConstraint(pattern);
        assertFalse(patternConstraint.verify(argumentationSystem.getSteps()));
    }

    @Test
    public void verifyConclusionReuseTwice() throws Exception {
        PatternConstraint patternConstraint=new ConclusionReuseConstraint(pattern);

        EvidenceRoleType rtExperimentation = new EvidenceRoleType("experimentation", ExperimentationConclusion.class);
        EvidenceRoleType rtResults = new EvidenceRoleType("result", Evidence.class);

        Evidence<Result> results0 = new Evidence<Result>("Result 0",new Result(new AList<>()));
        Conclusion<Effect> effect0 = new Conclusion<Effect>("Effect 0",new Effect());

        Evidence<Result> results1 = new Evidence<Result>("Result 1",new Result(new AList<>()));
        Conclusion<Effect> effect1 = new Conclusion<Effect>("Effect 1",new Effect());

        EvidenceRole evExperimentation0 = rtExperimentation.create(experimentation0);
        EvidenceRole evResults0 = rtResults.create(results0);
        EvidenceRole evResults1 = rtResults.create(results1);
        argumentationSystem.constructStep(argumentationSystem.getPattern("2"),Arrays.asList(new EvidenceRole[] {evExperimentation0,evResults0}), effect0);

        argumentationSystem.constructStep(argumentationSystem.getPattern("2"),Arrays.asList(new EvidenceRole[] {evExperimentation0,evResults1}), effect1);

        assertTrue(patternConstraint.verify(argumentationSystem.getSteps()));
    }

}