package fr.axonic.avek.engine;


import fr.axonic.avek.instance.conclusion.Experimentation;
import fr.axonic.avek.instance.conclusion.ExperimentationConclusion;
import fr.axonic.avek.instance.evidence.Stimulation;
import fr.axonic.avek.instance.evidence.Subject;
import fr.axonic.avek.instance.evidence.Result;
import fr.axonic.avek.instance.strategy.TreatStrategy;
import fr.axonic.avek.engine.evidence.Evidence;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.avek.engine.strategy.Strategy;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 23/06/16.
 */
public class PatternTest {

    @Test
    public void testApplicableWithNotGoodOrderEvidenceType() throws WrongEvidenceException {
        EvidenceRoleType rtStimulation = new EvidenceRoleType("stimulation", Stimulation.class);
        EvidenceRoleType rtSubject = new EvidenceRoleType("subject", Subject.class);
        ConclusionType conclusionExperimentationType = new ConclusionType(Experimentation.class);
        Strategy ts = new TreatStrategy();
        Pattern treat = new Pattern("Treat", ts, Arrays.asList(new EvidenceRoleType[] {rtStimulation, rtSubject}), conclusionExperimentationType);


        Evidence<Stimulation> stimulation0 = new Evidence<Stimulation>("Stimulation 0", new Stimulation());
        Evidence<Subject> subject0 = new Evidence<Subject>("Subject 0",new Subject());
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0",new Experimentation(), subject0.getElement(),stimulation0.getElement());

        EvidenceRole evStimulation0 = rtStimulation.create(stimulation0 );
        EvidenceRole evSubject0 = rtSubject.create(subject0);
        assertFalse(treat.applicable(Arrays.asList(new EvidenceRole[] {evSubject0,evStimulation0})));
    }

    @Test
    public void testApplicableWithNotGoodEvidenceType() throws WrongEvidenceException {
        EvidenceRoleType rtStimulation = new EvidenceRoleType("stimulation", Stimulation.class);
        EvidenceRoleType rtSubject = new EvidenceRoleType("subject", Subject.class);
        EvidenceRoleType rtResult = new EvidenceRoleType("subject", Result.class);
        ConclusionType conclusionExperimentationType = new ConclusionType(Experimentation.class);
        Strategy ts = new TreatStrategy();
        Pattern treat = new Pattern("Treat", ts, Arrays.asList(new EvidenceRoleType[] {rtStimulation, rtSubject}), conclusionExperimentationType);
        Evidence<Result> result = new Evidence<Result>("Result 0", new Result());
        Evidence<Subject> subject0 = new Evidence<Subject>("Subject 0",new Subject());
        EvidenceRole evResult0 = rtResult.create(result);
        EvidenceRole evSubject0 = rtSubject.create(subject0);
        assertFalse(treat.applicable(Arrays.asList(new EvidenceRole[] {evSubject0,evResult0})));
    }

    @Test(expected = StepBuildingException.class)
    public void testStepWithNotGoodOrderEvidenceType() throws WrongEvidenceException, StepBuildingException {
        EvidenceRoleType rtStimulation = new EvidenceRoleType("stimulation", Stimulation.class);
        EvidenceRoleType rtSubject = new EvidenceRoleType("subject", Subject.class);
        ConclusionType conclusionExperimentationType = new ConclusionType(Experimentation.class);
        Strategy ts = new TreatStrategy();
        Pattern treat = new Pattern("Treat", ts, Arrays.asList(new EvidenceRoleType[] {rtStimulation, rtSubject}), conclusionExperimentationType);


        Evidence<Stimulation> stimulation0 = new Evidence<Stimulation>("Stimulation 0", new Stimulation());
        Evidence<Subject> subject0 = new Evidence<Subject>("Subject 0",new Subject());
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0",new Experimentation(), subject0.getElement(),stimulation0.getElement());

        EvidenceRole evStimulation0 = rtStimulation.create(stimulation0 );
        EvidenceRole evSubject0 = rtSubject.create(subject0);
        treat.createStep(Arrays.asList(new EvidenceRole[] {evSubject0,evStimulation0}),experimentation0);
    }

    @Test
    public void testGoodStep() throws WrongEvidenceException, StepBuildingException {
        EvidenceRoleType rtStimulation = new EvidenceRoleType("stimulation", Stimulation.class);
        EvidenceRoleType rtSubject = new EvidenceRoleType("subject", Subject.class);
        ConclusionType conclusionExperimentationType = new ConclusionType(Experimentation.class);
        //Revoir car ici on a un singleton...
        Strategy ts = new TreatStrategy();
        Pattern treat = new Pattern("Treat", ts, Arrays.asList(new EvidenceRoleType[] {rtStimulation, rtSubject}), conclusionExperimentationType);


        Evidence<Stimulation> stimulation0 = new Evidence<Stimulation>("Stimulation 0", new Stimulation());
        Evidence<Subject> subject0 = new Evidence<Subject>("Subject 0",new Subject());
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0",new Experimentation(), subject0.getElement(),stimulation0.getElement());

        EvidenceRole evStimulation0 = rtStimulation.create(stimulation0 );
        EvidenceRole evSubject0 = rtSubject.create(subject0);
        Step step0 = treat.createStep(Arrays.asList(new EvidenceRole[] {evStimulation0,evSubject0}), experimentation0);
        assertNotNull(step0);
        assertNotNull(step0.getConclusion());
        assertNotNull(step0.getEvidences());
        assertNotNull(step0.getPattern());
        assertEquals(step0.getEvidences().size(),2);
    }

}