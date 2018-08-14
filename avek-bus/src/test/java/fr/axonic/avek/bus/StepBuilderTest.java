package fr.axonic.avek.bus;

import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.support.evidence.Document;
import fr.axonic.avek.instance.JustificationSystemEnum;
import fr.axonic.avek.instance.JustificationSystemFactory;
import fr.axonic.avek.instance.redmine.RedmineDocumentApproval;
import fr.axonic.avek.instance.redmine.RedmineDocumentEvidence;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * These tests use the known implementation of the Redmine JPD.
 */
public class StepBuilderTest {

    private StepBuilder stepBuilder;

    @Before
    public void initialize() throws VerificationException, WrongEvidenceException {
        stepBuilder = new StepBuilder(Collections.singletonList(JustificationSystemFactory.create(JustificationSystemEnum.REDMINE)));
    }

    @Test
    public void shouldNotBuildAnythingWithoutSupport() throws StrategyException, StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "A"));

        assertEquals(0, stepBuilder.getBuiltSteps().size());
    }

    @Ignore
    @Test
    public void shouldNotBuildWithUnexpectedSupports() throws StrategyException, StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_CTR_0001", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_CTR_0001", "A"));

        assertEquals(0, stepBuilder.getBuiltSteps().size());
    }

    @Ignore
    @Test
    public void shouldNotBuildWithSupportsWhenMissingDependency() throws StrategyException, StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0002", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0002", "A"));

        assertEquals(0, stepBuilder.getBuiltSteps().size());
    }

    @Ignore
    @Test
    public void shouldBuildWithAdequateSupports() throws StrategyException, StepBuildingException {
        stepBuilder.acknowledgeSupport(evidence("SWAM_ST_0001", "A"));
        stepBuilder.acknowledgeSupport(approval("SWAM_ST_0001", "A"));

        assertEquals(1, stepBuilder.getBuiltSteps().size());
        assertNotNull(stepBuilder.getBuiltSteps().get(0));
    }

    private static RedmineDocumentEvidence evidence(String name, String version) {
        Document document = new Document("http://aurl.com/" + name);
        document.setVersion(version);

        return new RedmineDocumentEvidence(name, document);
    }

    private static RedmineDocumentApproval approval(String name, String version) {
        String approvalName = name + "_APPROVAL";

        Document document = new Document("http://aurl.com/" + approvalName);
        document.setVersion(version);

        return new RedmineDocumentApproval(approvalName, document);
    }
}