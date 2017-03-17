package fr.axonic.avek.engine.constraint.pattern.intra;

import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.instance.conclusion.EstablishEffectConclusion;
import fr.axonic.avek.instance.conclusion.EstablishedEffect;
import fr.axonic.avek.instance.evidence.ResultsEvidence;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.avek.engine.constraint.PatternConstraint;
import fr.axonic.avek.engine.constraint.pattern.PatternConstraintTest;
import fr.axonic.avek.instance.conclusion.ExperimentationConclusion;
import fr.axonic.avek.instance.evidence.Result;
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

        InputType<ExperimentationConclusion> rtExperimentation = new InputType<>("experimentation", ExperimentationConclusion.class);
        InputType<ResultsEvidence> rtResults = new InputType<>("result", ResultsEvidence.class);

        ResultsEvidence results0 = new ResultsEvidence("Result 0",new Result(new AList<>()));
        EstablishEffectConclusion effect0 = new EstablishEffectConclusion("Effect 0",new EstablishedEffect(null, new AList<>()));

        SupportRole evExperimentation0 = rtExperimentation.create(experimentation0);
        SupportRole evResults0 = rtResults.create(results0);
        argumentationSystem.constructStep(argumentationSystem.getPattern("2"), Arrays.asList(new SupportRole[] {evExperimentation0,evResults0}), effect0);
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


        InputType<ExperimentationConclusion> rtExperimentation = new InputType<>("experimentation", ExperimentationConclusion.class);
        InputType<ResultsEvidence> rtResults = new InputType<>("result", ResultsEvidence.class);

        ResultsEvidence results0 = new ResultsEvidence("Result 0",new Result(new AList<>()));
        EstablishEffectConclusion effect0 = new EstablishEffectConclusion("Effect 0",new EstablishedEffect(null, new AList<>()));

        ResultsEvidence results1 = new ResultsEvidence("Result 1",new Result(new AList<>()));
        EstablishEffectConclusion effect1 = new EstablishEffectConclusion("Effect 1",new EstablishedEffect(null, new AList<>()));


        SupportRole evExperimentation0 = rtExperimentation.create(experimentation0);
        SupportRole evResults0 = rtResults.create(results0);
        SupportRole evResults1 = rtResults.create(results1);
        argumentationSystem.constructStep(argumentationSystem.getPattern("2"),Arrays.asList(new SupportRole[] {evExperimentation0,evResults0}), effect0);

        argumentationSystem.constructStep(argumentationSystem.getPattern("2"),Arrays.asList(new SupportRole[] {evExperimentation0,evResults1}), effect1);

        assertTrue(patternConstraint.verify(argumentationSystem.getSteps()));
    }

}