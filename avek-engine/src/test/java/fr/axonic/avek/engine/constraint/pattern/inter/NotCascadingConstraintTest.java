package fr.axonic.avek.engine.constraint.pattern.inter;

import fr.axonic.avek.engine.EvidenceRoleType;
import fr.axonic.avek.engine.Pattern;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 09/03/17.
 */
public class NotCascadingConstraintTest extends PatternConstraintTest{

    @Test
    public void verifyCascading() throws Exception {
        PatternConstraint patternConstraint=new NotCascadingConstraint(pattern,argumentationSystem.getPattern("2"));

        EvidenceRoleType rtExperimentation = new EvidenceRoleType("experimentation", ExperimentationConclusion.class);
        EvidenceRoleType rtResults = new EvidenceRoleType("result", Evidence.class);

        Evidence<Result> results0 = new Evidence<Result>("Result 0",new Result(new AList<>()));
        Conclusion<Effect> effect0 = new Conclusion<Effect>("Effect 0",new Effect());
        EvidenceRole evExperimentation0 = rtExperimentation.create(experimentation0);
        EvidenceRole evResults0 = rtResults.create(results0);
        argumentationSystem.constructStep(argumentationSystem.getPattern("2"), Arrays.asList(new EvidenceRole[] {evExperimentation0,evResults0}), effect0);
        assertFalse(patternConstraint.verify(argumentationSystem.getSteps()));
    }

    @Test
    public void verifyNotCascading() throws Exception {
        PatternConstraint patternConstraint=new NotCascadingConstraint(pattern,argumentationSystem.getPattern("3"));


        EvidenceRoleType rtExperimentation = new EvidenceRoleType("experimentation", ExperimentationConclusion.class);
        EvidenceRoleType rtResults = new EvidenceRoleType("result", Evidence.class);

        Evidence<Result> results0 = new Evidence<Result>("Result 0",new Result(new AList<>()));
        Conclusion<Effect> effect0 = new Conclusion<Effect>("Effect 0",new Effect());
        EvidenceRole evExperimentation0 = rtExperimentation.create(experimentation0);
        EvidenceRole evResults0 = rtResults.create(results0);
        argumentationSystem.constructStep(argumentationSystem.getPattern("2"), Arrays.asList(new EvidenceRole[] {evExperimentation0,evResults0}), effect0);
        assertTrue(patternConstraint.verify(argumentationSystem.getSteps()));
    }

}