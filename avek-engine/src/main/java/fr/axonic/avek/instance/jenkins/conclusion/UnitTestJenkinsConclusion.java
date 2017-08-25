package fr.axonic.avek.instance.jenkins.conclusion;

/**
 * Created by cduffau on 25/08/17.
 */
public class UnitTestJenkinsConclusion extends JenkinsConclusion{
    public UnitTestJenkinsConclusion() {
        this(null);
    }

    public UnitTestJenkinsConclusion(JenkinsStatus jenkinsStatus) {
        super("unit-test-jenkins", jenkinsStatus);
    }
}
