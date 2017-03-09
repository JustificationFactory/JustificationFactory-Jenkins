package fr.axonic.avek.engine.constraint.pattern;

import fr.axonic.avek.engine.*;
import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.Evidence;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.avek.engine.instance.conclusion.Effect;
import fr.axonic.avek.engine.instance.conclusion.Experimentation;
import fr.axonic.avek.engine.instance.conclusion.ExperimentationConclusion;
import fr.axonic.avek.engine.instance.evidence.Result;
import fr.axonic.avek.engine.instance.evidence.Stimulation;
import fr.axonic.avek.engine.instance.evidence.Subject;
import fr.axonic.avek.engine.provider.MockedArgumentationSystem;
import fr.axonic.avek.engine.strategy.Actor;
import fr.axonic.avek.engine.strategy.Role;
import fr.axonic.base.engine.AList;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 08/03/17.
 */
public abstract class PatternConstraintTest {

    protected Pattern pattern;
    protected ArgumentationSystemAPI argumentationSystem;
    protected ExperimentationConclusion experimentation0;
    protected EvidenceRole evStimulation0;
    protected EvidenceRole evSubject0;

    @Before
    public void setUp() throws VerificationException, WrongEvidenceException, StrategyException, StepBuildingException {
        pattern= MockedArgumentationSystem.getAXONICArgumentationSystem().getPattern("1");
        argumentationSystem=MockedArgumentationSystem.getAXONICArgumentationSystem();
        argumentationSystem.getPatternsBase().setConstraints(new ArrayList<>());
        Evidence<Stimulation> stimulation0 = new Evidence<Stimulation>("Stimulation 0", new Stimulation());
        Evidence<Subject> subject0 = new Evidence<Subject>("Subject 0",new Subject());
        experimentation0 = new ExperimentationConclusion("Experimentation 0",subject0.getElement(),stimulation0.getElement());
        EvidenceRoleType rtStimulation = new EvidenceRoleType("stimulation", Evidence.class);
        EvidenceRoleType rtSubject = new EvidenceRoleType("subject", Evidence.class);
        evStimulation0 = rtStimulation.create(stimulation0 );
        evSubject0 = rtSubject.create(subject0);

        argumentationSystem.constructStep(pattern,Arrays.asList(new EvidenceRole[] {evStimulation0,evSubject0}), experimentation0);
    }


}