package fr.axonic.avek.instance.jenkins.conclusion;

import fr.axonic.avek.engine.support.conclusion.Conclusion;

/**
 * Created by cduffau on 24/08/17.
 */
public class JenkinsConclusion extends Conclusion<JenkinsStatus> {
    public JenkinsConclusion(String name, JenkinsStatus jenkinsStatus) {
        super(name,jenkinsStatus);

    }


    public JenkinsConclusion() {
        super();
    }

    public JenkinsConclusion(String name) {
        super(name,null);
    }
}
