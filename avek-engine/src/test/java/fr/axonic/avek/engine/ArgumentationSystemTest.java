package fr.axonic.avek.engine;

import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.avek.engine.strategy.Actor;
import fr.axonic.avek.engine.strategy.Role;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.instance.MockedArgumentationSystem;
import fr.axonic.avek.instance.conclusion.ExperimentationConclusion;
import fr.axonic.avek.instance.evidence.Stimulation;
import fr.axonic.avek.instance.evidence.StimulationEvidence;
import fr.axonic.avek.instance.evidence.Subject;
import fr.axonic.avek.instance.evidence.SubjectEvidence;
import fr.axonic.avek.instance.strategy.TreatStrategy;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 17/03/17.
 */
public class ArgumentationSystemTest {

    private ArgumentationSystemAPI argumentationSystem;

    @Before
    public void setUp() throws VerificationException, WrongEvidenceException {
        argumentationSystem= MockedArgumentationSystem.getAXONICArgumentationSystem();
    }

    private void createAStep(Role role) throws VerificationException, WrongEvidenceException, StrategyException, StepBuildingException {
        Pattern pattern= MockedArgumentationSystem.getAXONICArgumentationSystem().getPattern("1");
        argumentationSystem=MockedArgumentationSystem.getAXONICArgumentationSystem();
        StimulationEvidence stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
        SubjectEvidence subject0 = new SubjectEvidence("Subject 0",new Subject());
        Actor actor0=new Actor("Chloé", role);
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0",subject0.getElement(),stimulation0.getElement());
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        InputType<Actor> rtActor=new InputType<>("actor", Actor.class);
        SupportRole evStimulation0 = rtStimulation.create(stimulation0 );
        SupportRole evSubject0 = rtSubject.create(subject0);
        SupportRole evActor0=rtActor.create(actor0);

        argumentationSystem.constructStep(pattern, Arrays.asList(evStimulation0,evSubject0,evActor0), experimentation0);

    }

    @Test
    public void getPattern() throws Exception {
        assertTrue(argumentationSystem.getPattern("1").getId().equals("1"));
    }

    @Test
    public void constructStep() throws Exception {
        createAStep(Role.SENIOR_EXPERT);
        assertTrue(argumentationSystem.getSteps().size()==1);
        assertTrue(argumentationSystem.getSteps().get(0).getPatternId().equals("1"));
        assertTrue(argumentationSystem.getSteps().get(0).getEvidences().size()==3);
        assertTrue(argumentationSystem.getSteps().get(0).getStrategy() instanceof TreatStrategy);
    }

    @Test(expected = StepBuildingException.class)
    public void constructStepWithWrongActorRole() throws WrongEvidenceException, StrategyException, VerificationException, StepBuildingException {
        createAStep(Role.TECHNICIAN);
    }
    @Test
    public void validate() throws Exception {
        createAStep(Role.SENIOR_EXPERT);
        assertTrue(argumentationSystem.getPatternsBase().getConstraints().size()>0);
        assertTrue(argumentationSystem.validate());
    }

    @Test
    public void resolveHypothesis() throws Exception {
        // TODO : create a test
    }


}