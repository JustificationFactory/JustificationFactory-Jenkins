package fr.axonic.avek.engine.constraint.step;

import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.instance.conclusion.EstablishEffectConclusion;
import fr.axonic.avek.instance.conclusion.EstablishedEffect;
import fr.axonic.avek.instance.evidence.ResultsEvidence;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.avek.engine.constraint.ArgumentationSystemConstraint;
import fr.axonic.avek.engine.constraint.PatternConstraintTest;
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
        ArgumentationSystemConstraint argumentationSystemConstraint =new ConclusionReuseConstraint(pattern);

        InputType<ExperimentationConclusion> rtExperimentation = new InputType<>("experimentation", ExperimentationConclusion.class);
        InputType<ResultsEvidence> rtResults = new InputType<>("result", ResultsEvidence.class);

        ResultsEvidence results0 = new ResultsEvidence("Result 0",new Result(new AList<>()));
        EstablishEffectConclusion effect0 = new EstablishEffectConclusion("Effect 0",new EstablishedEffect(null, new AList<>()));

        Support evExperimentation0 = rtExperimentation.create(experimentation0);
        Support evResults0 = rtResults.create(results0);
        argumentationSystem.constructStep(argumentationSystem.getPatternsBase().getPattern("2"), Arrays.asList(new Support[] {evExperimentation0,evResults0}), effect0);
        assertTrue(argumentationSystemConstraint.verify(argumentationSystem.getJustificationDiagram().getSteps()));
    }

    @Test
    public void verifyConclusionNotReuse() throws Exception {
        ArgumentationSystemConstraint argumentationSystemConstraint =new ConclusionReuseConstraint(pattern);
        assertFalse(argumentationSystemConstraint.verify(argumentationSystem.getJustificationDiagram().getSteps()));
    }

    @Test
    public void verifyConclusionReuseTwice() throws Exception {
        ArgumentationSystemConstraint argumentationSystemConstraint =new ConclusionReuseConstraint(pattern);


        InputType<ExperimentationConclusion> rtExperimentation = new InputType<>("experimentation", ExperimentationConclusion.class);
        InputType<ResultsEvidence> rtResults = new InputType<>("result", ResultsEvidence.class);

        ResultsEvidence results0 = new ResultsEvidence("Result 0",new Result(new AList<>()));
        EstablishEffectConclusion effect0 = new EstablishEffectConclusion("Effect 0",new EstablishedEffect(null, new AList<>()));

        ResultsEvidence results1 = new ResultsEvidence("Result 1",new Result(new AList<>()));
        EstablishEffectConclusion effect1 = new EstablishEffectConclusion("Effect 1",new EstablishedEffect(null, new AList<>()));


        Support evExperimentation0 = rtExperimentation.create(experimentation0);
        Support evResults0 = rtResults.create(results0);
        Support evResults1 = rtResults.create(results1);
        argumentationSystem.constructStep(argumentationSystem.getPatternsBase().getPattern("2"),Arrays.asList(new Support[] {evExperimentation0,evResults0}), effect0);

        argumentationSystem.constructStep(argumentationSystem.getPatternsBase().getPattern("2"),Arrays.asList(new Support[] {evExperimentation0,evResults1}), effect1);

        assertTrue(argumentationSystemConstraint.verify(argumentationSystem.getJustificationDiagram().getSteps()));
    }

}