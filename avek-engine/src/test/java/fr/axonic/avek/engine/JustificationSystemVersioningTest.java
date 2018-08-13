package fr.axonic.avek.engine;

import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.DiagramPatternsBase;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.avek.engine.strategy.Actor;
import fr.axonic.avek.engine.strategy.Role;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.instance.MockedJustificationSystem;
import fr.axonic.avek.instance.conclusion.ExperimentationConclusion;
import fr.axonic.avek.instance.evidence.Stimulation;
import fr.axonic.avek.instance.evidence.StimulationEvidence;
import fr.axonic.avek.instance.evidence.Subject;
import fr.axonic.avek.instance.evidence.SubjectEvidence;
import fr.axonic.avek.instance.strategy.TreatStrategy;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JustificationSystemVersioningTest {

    private JustificationSystem<DiagramPatternsBase> argumentationSystem;

    @Before
    public void setUp() throws VerificationException, WrongEvidenceException {
        argumentationSystem= new MockedJustificationSystem<>(MockedJustificationSystem.getAXONICDiagramPattern());
        argumentationSystem.versioningEnable=true;
    }

    private void createAStep(Role role, String version) throws VerificationException, WrongEvidenceException, StrategyException, StepBuildingException {
        Pattern pattern= argumentationSystem.getPatternsBase().getPattern("1");
        Stimulation stimulation=new Stimulation();
        stimulation.setVersion(version);
        StimulationEvidence stimulation0 = new StimulationEvidence("Stimulation 0",stimulation);
        SubjectEvidence subject0 = new SubjectEvidence("Subject 0",new Subject());
        Actor actor0=new Actor("Chloé", role);
        ExperimentationConclusion experimentation0 = new ExperimentationConclusion("Experimentation 0",subject0.getElement(),stimulation0.getElement());
        InputType<StimulationEvidence> rtStimulation = new InputType<>("stimulation", StimulationEvidence.class);
        InputType<SubjectEvidence> rtSubject = new InputType<>("subject", SubjectEvidence.class);
        InputType<Actor> rtActor=new InputType<>("actor", Actor.class);
        Support evStimulation0 = rtStimulation.create(stimulation0 );
        Support evSubject0 = rtSubject.create(subject0);
        Support evActor0=rtActor.create(actor0);

        argumentationSystem.constructStep(pattern, Arrays.asList(evStimulation0,evSubject0,evActor0), experimentation0);

    }

    @Test
    public void constructStep() throws Exception {
        createAStep(Role.SENIOR_EXPERT,"0");
        assertEquals(1, argumentationSystem.getJustificationDiagram().getSteps().size());
        assertEquals("1", argumentationSystem.getJustificationDiagram().getSteps().get(0).getPatternId());
        assertEquals(3, argumentationSystem.getJustificationDiagram().getSteps().get(0).getSupports().size());
        assertTrue(argumentationSystem.getJustificationDiagram().getSteps().get(0).getStrategy() instanceof TreatStrategy);
    }

    @Test
    public void constructStepToUpdateVersion() throws Exception {
        createAStep(Role.SENIOR_EXPERT,"0");
        assertEquals(1,argumentationSystem.getJustificationDiagram().getSteps().size());
        assertEquals("1",argumentationSystem.getJustificationDiagram().getSteps().get(0).getPatternId());
        assertEquals(3,argumentationSystem.getJustificationDiagram().getSteps().get(0).getSupports().size());
        assertTrue(argumentationSystem.getJustificationDiagram().getSteps().get(0).getStrategy() instanceof TreatStrategy);
        assertEquals("0", argumentationSystem.getJustificationDiagram().getSteps().get(0).getSupports().get(0).getElement().getVersion());
        assertEquals(1, argumentationSystem.getJustificationDiagram().getSteps().get(0).getSupports().get(0).getArtifacts().size());
        createAStep(Role.SENIOR_EXPERT,"1");
        assertEquals(1,argumentationSystem.getJustificationDiagram().getSteps().size());
        assertEquals("1", argumentationSystem.getJustificationDiagram().getSteps().get(0).getPatternId());
        assertEquals(3,argumentationSystem.getJustificationDiagram().getSteps().get(0).getSupports().size());
        assertTrue(argumentationSystem.getJustificationDiagram().getSteps().get(0).getStrategy() instanceof TreatStrategy);
        assertEquals("1", argumentationSystem.getJustificationDiagram().getSteps().get(0).getSupports().get(0).getElement().getVersion());
        assertEquals(2, argumentationSystem.getJustificationDiagram().getSteps().get(0).getSupports().get(0).getArtifacts().size());
    }
}
