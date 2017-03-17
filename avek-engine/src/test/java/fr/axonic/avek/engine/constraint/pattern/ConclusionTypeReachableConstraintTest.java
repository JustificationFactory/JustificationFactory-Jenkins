package fr.axonic.avek.engine.constraint.pattern;

import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.avek.engine.pattern.type.OutputType;
import fr.axonic.avek.engine.strategy.Rationale;
import fr.axonic.avek.engine.strategy.Strategy;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.instance.conclusion.EstablishEffectConclusion;
import fr.axonic.avek.instance.conclusion.GeneralizationConclusion;
import fr.axonic.avek.instance.evidence.ResultsEvidence;
import fr.axonic.avek.instance.evidence.Stimulation;
import fr.axonic.avek.instance.strategy.GeneralizeStrategy;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 16/03/17.
 */
public class ConclusionTypeReachableConstraintTest extends PatternConstraintTest{


    @Test
    public void testConclusionTypeReachable(){
        ConclusionTypeReachableConstraint conclusionTypeReachableConstraint=new ConclusionTypeReachableConstraint(new OutputType<>(GeneralizationConclusion.class));
        assertTrue(conclusionTypeReachableConstraint.verify(argumentationSystem.getPatternsBase()));

    }

    @Test
    public void testConclusionTypeMultipleReachable(){
        InputType<EstablishEffectConclusion> rtEstablishedEffect= new InputType<>("effects", EstablishEffectConclusion.class);
        InputType<EstablishEffectConclusion> rtEstablishedEffect2= new InputType<>("effects", EstablishEffectConclusion.class);
        InputType<ResultsEvidence> rtResult = new InputType<>("experimentation", ResultsEvidence.class);
        OutputType<GeneralizationConclusion> conclusionGeneralizationType = new OutputType<>(GeneralizationConclusion.class);
        Strategy ts3=new GeneralizeStrategy(new Rationale<>(null),null);
        Pattern pattern=new Pattern("3", "Generalize", ts3, Arrays.asList(new InputType[]{rtEstablishedEffect,rtEstablishedEffect2,rtResult}),conclusionGeneralizationType);

        argumentationSystem.getPatternsBase().addPattern(pattern);
        ConclusionTypeReachableConstraint conclusionTypeReachableConstraint=new ConclusionTypeReachableConstraint(new OutputType<>(GeneralizationConclusion.class));
        assertTrue(conclusionTypeReachableConstraint.verify(argumentationSystem.getPatternsBase()));

    }

    @Test
    public void testConclusionTypeNotReachable(){
        class OtherConclusion extends Conclusion<Stimulation>{

        }
        ConclusionTypeReachableConstraint conclusionTypeReachableConstraint=new ConclusionTypeReachableConstraint(new OutputType<>(OtherConclusion.class));
        assertFalse(conclusionTypeReachableConstraint.verify(argumentationSystem.getPatternsBase()));

    }
}