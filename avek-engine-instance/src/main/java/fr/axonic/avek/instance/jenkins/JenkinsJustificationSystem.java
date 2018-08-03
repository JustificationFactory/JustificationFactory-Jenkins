package fr.axonic.avek.instance.jenkins;

import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.ListPatternsBase;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.PatternsBase;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.avek.engine.pattern.type.OutputType;
import fr.axonic.avek.engine.support.instance.DocumentEvidence;
import fr.axonic.avek.instance.jenkins.conclusion.IntegrationTestJenkinsConclusion;
import fr.axonic.avek.instance.jenkins.conclusion.UnitTestJenkinsConclusion;
import fr.axonic.validation.exception.VerificationException;

import java.util.Arrays;

/**
 * Created by cduffau on 25/08/17.
 */
public class JenkinsJustificationSystem extends JustificationSystem<ListPatternsBase> {

    public JenkinsJustificationSystem() throws VerificationException, WrongEvidenceException {
        super();
        ListPatternsBase patternsBase=new ListPatternsBase();
        InputType<DocumentEvidence> documentType=new InputType<>("document",DocumentEvidence.class);
        OutputType<UnitTestJenkinsConclusion> utType=new OutputType<>(UnitTestJenkinsConclusion.class);
        OutputType<IntegrationTestJenkinsConclusion> itType=new OutputType<>(IntegrationTestJenkinsConclusion.class);
        Pattern unitPattern=new Pattern("unit-test","Unit Test Pattern", new JenkinsStrategy("Validate unit tests"),Arrays.asList(documentType),utType);
        Pattern integrationPattern=new Pattern("integration-test","Integration pattern", new JenkinsStrategy("Validate integration tests"), Arrays.asList(documentType,utType.transformToInput()),itType);

        patternsBase.addPattern(unitPattern);
        patternsBase.addPattern(integrationPattern);
        this.patternsBase=patternsBase;
        this.autoSupportFillEnable =true;
        this.versioningEnable=true;

    }


}