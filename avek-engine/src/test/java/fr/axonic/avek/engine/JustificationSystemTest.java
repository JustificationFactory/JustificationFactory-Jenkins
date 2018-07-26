package fr.axonic.avek.engine;

import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.JustificationStep;
import fr.axonic.avek.engine.pattern.ListPatternsBase;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.avek.engine.strategy.Actor;
import fr.axonic.avek.engine.strategy.Role;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Document;
import fr.axonic.avek.engine.support.instance.DocumentEvidence;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.instance.MockedArgumentationSystem;
import fr.axonic.avek.instance.avek.conclusion.ExperimentationConclusion;
import fr.axonic.avek.instance.avek.evidence.Stimulation;
import fr.axonic.avek.instance.avek.evidence.StimulationEvidence;
import fr.axonic.avek.instance.avek.evidence.Subject;
import fr.axonic.avek.instance.avek.evidence.SubjectEvidence;
import fr.axonic.avek.instance.avek.strategy.TreatStrategy;
import fr.axonic.avek.instance.jenkins.conclusion.IntegrationTestJenkinsConclusion;
import fr.axonic.avek.instance.jenkins.conclusion.JenkinsStatus;
import fr.axonic.avek.instance.jenkins.conclusion.JenkinsStatusEnum;
import fr.axonic.avek.instance.jenkins.conclusion.UnitTestJenkinsConclusion;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 17/03/17.
 */
public class JustificationSystemTest {

    private JustificationSystem<ListPatternsBase> argumentationSystem;

    @Before
    public void setUp() throws VerificationException, WrongEvidenceException {
        argumentationSystem= MockedArgumentationSystem.getAXONICArgumentationSystem();
    }

    private void createAStep(Role role) throws VerificationException, WrongEvidenceException, StrategyException, StepBuildingException {
        Pattern pattern= MockedArgumentationSystem.getAXONICArgumentationSystem().getPatternsBase().getPattern("1");
        argumentationSystem=MockedArgumentationSystem.getAXONICArgumentationSystem();
        StimulationEvidence stimulation0 = new StimulationEvidence("Stimulation 0", new Stimulation());
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
    public void getPattern() throws Exception {
        assertTrue(argumentationSystem.getPatternsBase().getPattern("1").getId().equals("1"));
    }

    @Test
    public void constructStep() throws Exception {
        createAStep(Role.SENIOR_EXPERT);
        assertTrue(argumentationSystem.getJustificationDiagram().getSteps().size()==1);
        assertTrue(argumentationSystem.getJustificationDiagram().getSteps().get(0).getPatternId().equals("1"));
        assertTrue(argumentationSystem.getJustificationDiagram().getSteps().get(0).getSupports().size()==3);
        assertTrue(argumentationSystem.getJustificationDiagram().getSteps().get(0).getStrategy() instanceof TreatStrategy);
    }
    @Test
    public void constructStepWithAutoFill() throws Exception {
        JustificationSystemAPI argumentationSystem=MockedArgumentationSystem.getJenkinsArgumentationSystem();
        Pattern unitPattern=argumentationSystem.getPatternsBase().getPattern("unit-test");
        Pattern integrationPattern=argumentationSystem.getPatternsBase().getPattern("integration-test");
        Evidence documentEvidence=new DocumentEvidence("document",new Document("test"));
        Conclusion unitCcl=new UnitTestJenkinsConclusion(new JenkinsStatus(JenkinsStatusEnum.OK));
        Conclusion itCcl=new IntegrationTestJenkinsConclusion(new JenkinsStatus(JenkinsStatusEnum.OK));
        InputType<UnitTestJenkinsConclusion> utInputType=new InputType<>("unit-test",UnitTestJenkinsConclusion.class);
        InputType<DocumentEvidence> documentInputType=new InputType<>("document",DocumentEvidence.class);
        argumentationSystem.constructStep(unitPattern, Collections.singletonList(documentInputType.create(documentEvidence)),unitCcl);
        argumentationSystem.constructStep(integrationPattern,Arrays.asList(documentInputType.create(documentEvidence)),itCcl);
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