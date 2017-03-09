package fr.axonic.avek.engine;

import fr.axonic.avek.engine.instance.evidence.Stimulation;
import fr.axonic.avek.engine.instance.evidence.Subject;
import fr.axonic.avek.engine.evidence.Evidence;
import fr.axonic.avek.engine.evidence.EvidenceRole;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 24/06/16.
 */
public class EvidenceRoleTypeTest {

    @Test
    public void testGoodEvidenceCreation() throws WrongEvidenceException, VerificationException {
        EvidenceRoleType<Evidence> evidenceRoleType= new EvidenceRoleType<Evidence>("stimulation type",Evidence.class);
        Evidence<Stimulation> evidence=new Evidence<Stimulation>("stimulation", new Stimulation());
        evidenceRoleType.create(evidence);
    }

    @Test(expected=WrongEvidenceException.class)
    public void testWrongEvidenceCreation() throws WrongEvidenceException, VerificationException {
        EvidenceRoleType<Evidence> evidenceRoleType= new EvidenceRoleType<Evidence>("stimulation type",Evidence.class);

        Evidence<Subject> evidence=new Evidence<Subject>("subject", new Subject());
        evidenceRoleType.create(evidence);
    }

    @Test
    public void testGoodEvidenceRoleFromEvidenceRoleType() throws WrongEvidenceException, VerificationException {
        EvidenceRoleType<Evidence> evidenceRoleType= new EvidenceRoleType<Evidence>("stimulation type",Evidence.class);

        Evidence<Stimulation> evidence=new Evidence<Stimulation>("stimulation", new Stimulation());
        EvidenceRole role=evidenceRoleType.create(evidence);
        assertTrue(evidenceRoleType.isEvidenceType(role.getSupport()));
    }
    @Test
    public void testWrongEvidenceRoleFromEvidenceRoleType() throws WrongEvidenceException, VerificationException {
        EvidenceRoleType<Evidence> evidenceRoleType= new EvidenceRoleType<Evidence>("stimulation type",Evidence.class);

        EvidenceRole evidenceRole=new EvidenceRole("test",new Evidence("test",new Subject()));
        assertFalse(evidenceRoleType.isEvidenceType(evidenceRole.getSupport()));
    }


}