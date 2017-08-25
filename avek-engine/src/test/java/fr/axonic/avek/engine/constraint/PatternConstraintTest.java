package fr.axonic.avek.engine.constraint;

import fr.axonic.avek.engine.*;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.strategy.Actor;
import fr.axonic.avek.engine.strategy.Role;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.instance.avek.conclusion.ExperimentationConclusion;
import fr.axonic.avek.instance.avek.evidence.Stimulation;
import fr.axonic.avek.instance.avek.evidence.StimulationEvidence;
import fr.axonic.avek.instance.avek.evidence.Subject;
import fr.axonic.avek.instance.avek.evidence.SubjectEvidence;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.instance.MockedArgumentationSystem;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cduffau on 08/03/17.
 */
public abstract class PatternConstraintTest {

    protected Pattern pattern;
    protected ArgumentationSystemAPI argumentationSystem;
    protected ExperimentationConclusion experimentation0;
    protected SupportRole evStimulation0;
    protected SupportRole evSubject0;
    protected SupportRole evActor0;

    @Before
    public void setUp() throws VerificationException, WrongEvidenceException, StrategyException, StepBuildingException {
        pattern= MockedArgumentationSystem.getAXONICArgumentationSystem().getPattern("1");
        argumentationSystem=MockedArgumentationSystem.getAXONICArgumentationSystem();
        argumentationSystem.getPatternsBase().setConstraints(new ArrayList<>());
        StimulationEvidence stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
        SubjectEvidence subject0 = new SubjectEvidence("Subject 0",new Subject());
        Actor actor=new Actor("Chloé", Role.SENIOR_EXPERT);
        experimentation0 = new ExperimentationConclusion("Experimentation 0",subject0.getElement(),stimulation0.getElement());
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        InputType<Actor> rtActor = new InputType<>("actor", Actor.class);
        evStimulation0 = rtStimulation.create(stimulation0 );
        evSubject0 = rtSubject.create(subject0);
        evActor0=rtActor.create(actor);

        argumentationSystem.constructStep(pattern,Arrays.asList(evStimulation0,evSubject0,evActor0), experimentation0);
    }


}