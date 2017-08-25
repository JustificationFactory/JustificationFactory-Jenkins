package fr.axonic.avek.instance.jenkins.conclusion;

/**
 * Created by cduffau on 25/08/17.
 */
public class IntegrationTestJenkinsConclusion extends JenkinsConclusion {

    public IntegrationTestJenkinsConclusion() {
        this(null);
    }

    public IntegrationTestJenkinsConclusion(JenkinsStatus jenkinsStatus) {
        super("integration-test-jenkins", jenkinsStatus);
    }
}
