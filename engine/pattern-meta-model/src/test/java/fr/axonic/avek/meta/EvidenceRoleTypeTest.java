package fr.axonic.avek.meta;

import fr.axonic.avek.instance.evidence.Stimulation;
import fr.axonic.avek.instance.evidence.Subject;
import fr.axonic.avek.meta.evidence.Evidence;
import fr.axonic.avek.meta.evidence.EvidenceRole;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cduffau on 24/06/16.
 */
public class EvidenceRoleTypeTest {

    @Test
    public void testGoodEvidenceCreation() throws WrongEvidenceException {
        EvidenceRoleType<Stimulation> evidenceRoleType= new EvidenceRoleType<Stimulation>("stimulation type",Stimulation.class);
        Evidence<Stimulation> evidence=new Evidence<Stimulation>("stimulation", new Stimulation());
        evidenceRoleType.create(evidence);
    }

    @Test(expected=WrongEvidenceException.class)
    public void testWrongEvidenceCreation() throws WrongEvidenceException {
        EvidenceRoleType<Stimulation> evidenceRoleType= new EvidenceRoleType<Stimulation>("stimulation type",Stimulation.class);
        Evidence<Subject> evidence=new Evidence<Subject>("subject", new Subject());
        evidenceRoleType.create(evidence);
    }

    @Test
    public void testGoodEvidenceRoleFromEvidenceRoleType() throws WrongEvidenceException {
        EvidenceRoleType<Stimulation> evidenceRoleType= new EvidenceRoleType<Stimulation>("stimulation type",Stimulation.class);
        Evidence<Stimulation> evidence=new Evidence<Stimulation>("stimulation", new Stimulation());
        EvidenceRole role=evidenceRoleType.create(evidence);
        assertTrue(evidenceRoleType.isEvidenceType(role.getEvidence()));
    }
    @Test
    public void testWrongEvidenceRoleFromEvidenceRoleType() throws WrongEvidenceException {
        EvidenceRoleType<Stimulation> evidenceRoleType= new EvidenceRoleType<Stimulation>("stimulation type",Stimulation.class);
        EvidenceRole evidenceRole=new EvidenceRole("test",new Evidence("test",new Subject()));
        assertFalse(evidenceRoleType.isEvidenceType(evidenceRole.getEvidence()));
    }


}