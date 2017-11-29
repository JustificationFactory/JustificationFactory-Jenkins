package fr.axonic.avek.instance.jenkins;

import fr.axonic.avek.engine.strategy.ComputedStrategy;
import fr.axonic.avek.engine.strategy.Rationale;
import fr.axonic.avek.engine.strategy.UsageDomain;

/**
 * Created by cduffau on 25/08/17.
 */
public class JenkinsStrategy extends ComputedStrategy{

    public JenkinsStrategy() {
        super();
    }

    public JenkinsStrategy(String name) {
        super(name, null,null);
    }
}
