package fr.axonic.avek.engine.constraint.step;

import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.instance.conclusion.EstablishEffectConclusion;
import fr.axonic.avek.instance.conclusion.EstablishedEffect;
import fr.axonic.avek.instance.evidence.ResultsEvidence;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.avek.engine.constraint.JustificationSystemConstraint;
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
public class NotCascadingConstraintTest extends PatternConstraintTest{

    @Test
    public void verifyCascading() throws Exception {
        JustificationSystemConstraint justificationSystemConstraint =new NotCascadingConstraint(pattern,argumentationSystem.getPatternsBase().getPattern("2"));


        InputType<ExperimentationConclusion> rtExperimentation = new InputType<>("experimentation", ExperimentationConclusion.class);
        InputType<ResultsEvidence> rtResults = new InputType<>("result", ResultsEvidence.class);

        ResultsEvidence results0 = new ResultsEvidence("Result 0",new Result(new AList<>()));
        EstablishEffectConclusion effect0 = new EstablishEffectConclusion("Effect 0",new EstablishedEffect(null, new AList<>()));
        Support evExperimentation0 = rtExperimentation.create(experimentation0);
        Support evResults0 = rtResults.create(results0);
        argumentationSystem.constructStep(argumentationSystem.getPatternsBase().getPattern("2"), Arrays.asList(new Support[] {evExperimentation0,evResults0}), effect0);
        assertFalse(justificationSystemConstraint.verify(argumentationSystem.getJustificationDiagram().getSteps()));
    }

    @Test
    public void verifyNotCascading() throws Exception {
        JustificationSystemConstraint justificationSystemConstraint =new NotCascadingConstraint(pattern,argumentationSystem.getPatternsBase().getPattern("3"));


        InputType<ExperimentationConclusion> rtExperimentation = new InputType<>("experimentation", ExperimentationConclusion.class);
        InputType<ResultsEvidence> rtResults = new InputType<>("result", ResultsEvidence.class);

        ResultsEvidence results0 = new ResultsEvidence("Result 0",new Result(new AList<>()));
        EstablishEffectConclusion effect0 = new EstablishEffectConclusion("Effect 0",new EstablishedEffect(null, new AList<>()));
        Support evExperimentation0 = rtExperimentation.create(experimentation0);
        Support evResults0 = rtResults.create(results0);
        argumentationSystem.constructStep(argumentationSystem.getPatternsBase().getPattern("2"), Arrays.asList(new Support[] {evExperimentation0,evResults0}), effect0);
        assertTrue(justificationSystemConstraint.verify(argumentationSystem.getJustificationDiagram().getSteps()));
    }

}