package fr.axonic.avek.engine.pattern.type;

import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.instance.conclusion.ExperimentationConclusion;
import fr.axonic.avek.instance.evidence.Stimulation;
import fr.axonic.avek.instance.evidence.StimulationEvidence;
import fr.axonic.avek.instance.evidence.Subject;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 17/03/17.
 */
public class InputTypeTest {
    @Test
    public void testGoodEvidenceCreation() throws WrongEvidenceException, VerificationException {
        InputType<Evidence> evidenceRoleType= new InputType<Evidence>("stimulation type",Evidence.class);
        Evidence<Stimulation> evidence=new Evidence<Stimulation>("stimulation", new Stimulation());
        evidenceRoleType.create(evidence);
    }

    @Test(expected=WrongEvidenceException.class)
    public void testWrongEvidenceCreation() throws WrongEvidenceException, VerificationException {
        InputType<StimulationEvidence> evidenceRoleType= new InputType<>("stimulation type",StimulationEvidence.class);

        Evidence<Subject> evidence=new Evidence<Subject>("subject", new Subject());
        evidenceRoleType.create(evidence);
    }

    @Test
    public void testGoodEvidenceRoleFromEvidenceRoleType() throws WrongEvidenceException, VerificationException {
        InputType<Evidence> evidenceRoleType= new InputType<Evidence>("stimulation type",Evidence.class);

        Evidence<Stimulation> evidence=new Evidence<Stimulation>("stimulation", new Stimulation());
        Support role=evidenceRoleType.create(evidence);
        assertTrue(evidenceRoleType.check(role));
    }
    @Test
    public void testWrongEvidenceRoleFromEvidenceRoleType() throws WrongEvidenceException, VerificationException {
        InputType<StimulationEvidence> evidenceRoleType= new InputType<>("stimulation type",StimulationEvidence.class);

        Support supportRole =new Evidence("test",new Subject());
        assertFalse(evidenceRoleType.check(supportRole));
    }

    @Test
    public void isPrimitiveInputType() throws Exception {
        InputType<StimulationEvidence> evidenceRoleType= new InputType<>("stimulation type",StimulationEvidence.class);
        assertTrue(evidenceRoleType.isPrimitiveInputType());
    }

    @Test
    public void isNotPrimitiveInputType() throws Exception {
        InputType<ExperimentationConclusion> evidenceRoleType= new InputType<>("stimulation type",ExperimentationConclusion.class);
        assertFalse(evidenceRoleType.isPrimitiveInputType());
    }

}