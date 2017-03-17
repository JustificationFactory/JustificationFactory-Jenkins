package fr.axonic.avek.engine;


import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.instance.conclusion.ExperimentationConclusion;
import fr.axonic.avek.instance.evidence.*;
import fr.axonic.avek.instance.strategy.TreatStrategy;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.engine.strategy.*;
import fr.axonic.avek.engine.pattern.type.OutputType;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.base.engine.AList;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 23/06/16.
 */
public class PatternTest {

    @Test
    public void testApplicableWithDifferentOrderEvidenceType() throws WrongEvidenceException, VerificationException {
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        OutputType<ExperimentationConclusion> conclusionExperimentationType = new OutputType<>(ExperimentationConclusion.class);
        class TestProject implements Project {

        }
        Strategy ts = new TreatStrategy(new Rationale<>(new TestProject()), null);
        Pattern treat = new Pattern("Treat", ts, Arrays.asList(new InputType[] {rtStimulation, rtSubject}), conclusionExperimentationType);


        StimulationEvidence stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
        Evidence<Subject> subject0 = new SubjectEvidence("Subject 0",new Subject());
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0", subject0.getElement(),stimulation0.getElement());

        SupportRole evStimulation0 = rtStimulation.create(stimulation0 );
        SupportRole evSubject0 = rtSubject.create(subject0);
        assertTrue(treat.applicable(Arrays.asList(new SupportRole[] {evSubject0,evStimulation0})));
    }

    @Test
    @Ignore
    public void testApplicableWithOptionalEvidenceType() throws WrongEvidenceException, VerificationException {
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        OutputType<ExperimentationConclusion> conclusionExperimentationType = new OutputType<>(ExperimentationConclusion.class);

        class TestProject implements Project{

        }
        Strategy ts = new TreatStrategy(new Rationale<>(new TestProject()), null);
        Pattern treat = new Pattern("Treat", ts, Arrays.asList(new InputType[] {rtStimulation, rtSubject}), conclusionExperimentationType);


        Evidence<Stimulation> stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
        Evidence<Subject> subject0 = new SubjectEvidence("Subject 0",new Subject());
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0", subject0.getElement(),stimulation0.getElement());

        SupportRole evStimulation0 = rtStimulation.create(stimulation0 );
        SupportRole evSubject0 = rtSubject.create(subject0);
        assertTrue(treat.applicable(Arrays.asList(new SupportRole[] {evSubject0,evStimulation0})));
    }

    @Test
    @Ignore
    public void testApplicableWithOptionalNotGiveEvidenceType() throws WrongEvidenceException, VerificationException {
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        //rtSubject.setOptional(true);
        OutputType<ExperimentationConclusion> conclusionExperimentationType = new OutputType<>(ExperimentationConclusion.class);
        class TestProject implements Project{

        }
        Strategy ts = new TreatStrategy(new Rationale<>(new TestProject()), null);
        Pattern treat = new Pattern("Treat", ts, Arrays.asList(new InputType[] {rtStimulation, rtSubject}), conclusionExperimentationType);


        StimulationEvidence stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());

        SupportRole evStimulation0 = rtStimulation.create(stimulation0 );
        assertTrue(treat.applicable(Arrays.asList(new SupportRole[] {evStimulation0})));
    }


    @Test(expected = WrongEvidenceException.class)
    public void testApplicableWithNotGoodEvidenceType() throws WrongEvidenceException, VerificationException {
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        InputType<SubjectEvidence> rtResult = new InputType<>("subject", SubjectEvidence.class);
        OutputType<ExperimentationConclusion> conclusionExperimentationType = new OutputType<>(ExperimentationConclusion.class);

        class TestProject implements Project{

        }
        Strategy ts = new TreatStrategy(new Rationale<>(new TestProject()), null);
        Pattern treat = new Pattern("Treat", ts, Arrays.asList(new InputType[] {rtStimulation, rtSubject}), conclusionExperimentationType);
        Evidence<Result> result = new ResultsEvidence("Result 0", new Result(new AList<>()));
        Evidence<Subject> subject0 = new SubjectEvidence("Subject 0",new Subject());
        SupportRole evResult0 = rtResult.create(result);
        SupportRole evSubject0 = rtSubject.create(subject0);
        assertFalse(treat.applicable(Arrays.asList(new SupportRole[] {evSubject0,evResult0})));
    }

    @Test(expected = StepBuildingException.class)
    public void testStepWithNotGoodOrderEvidenceType() throws WrongEvidenceException, StepBuildingException, VerificationException, StrategyException {
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        OutputType<ExperimentationConclusion> conclusionExperimentationType = new OutputType<>(ExperimentationConclusion.class);

        class TestProject implements Project{

        }
        Strategy ts = new TreatStrategy(new Rationale<>(new TestProject()), null);
        Pattern treat = new Pattern("Treat", ts, Arrays.asList(new InputType[] {rtStimulation, rtSubject}), conclusionExperimentationType);


        Evidence<Stimulation> stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
        Evidence<Subject> subject0 = new SubjectEvidence("Subject 0",new Subject());
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0", subject0.getElement(),stimulation0.getElement());

        SupportRole evStimulation0 = rtStimulation.create(stimulation0 );
        SupportRole evSubject0 = rtSubject.create(subject0);
        treat.createStep(Arrays.asList(new SupportRole[] {evSubject0,evStimulation0}),experimentation0,new Actor("Toto", Role.INTERMEDIATE_EXPERT));
    }

    @Test
    public void testGoodStep() throws WrongEvidenceException, StepBuildingException, VerificationException, StrategyException {
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        OutputType<ExperimentationConclusion> conclusionExperimentationType = new OutputType<>(ExperimentationConclusion.class);

        //Revoir car ici on a un singleton...
        class TestProject implements Project{

        }
        Strategy ts = new TreatStrategy(new Rationale<>(new TestProject()), null);
        Pattern treat = new Pattern("Treat", ts, Arrays.asList(new InputType[] {rtStimulation, rtSubject}), conclusionExperimentationType);


        Evidence<Stimulation> stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
        Evidence<Subject> subject0 = new SubjectEvidence("Subject 0",new Subject());
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0", subject0.getElement(),stimulation0.getElement());

        SupportRole evStimulation0 = rtStimulation.create(stimulation0 );
        SupportRole evSubject0 = rtSubject.create(subject0);
        Step step0 = treat.createStep(Arrays.asList(new SupportRole[] {evStimulation0,evSubject0}), experimentation0,new Actor("Toto", Role.INTERMEDIATE_EXPERT));
        assertNotNull(step0);
        assertNotNull(step0.getConclusion());
        assertNotNull(step0.getEvidences());
        assertNotNull(step0.getStrategy());
        assertEquals(step0.getEvidences().size(),2);
    }

}