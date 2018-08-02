package fr.axonic.avek.instance.jenkins.conclusion;

/**
 * Created by cduffau on 25/08/17.
 */
public class StressTestJenkinsConclusion extends JenkinsConclusion{
    public StressTestJenkinsConclusion() {
        super();
    }

    public StressTestJenkinsConclusion(JenkinsStatus jenkinsStatus) {
        super("stress-test-jenkins", jenkinsStatus);
    }
}
