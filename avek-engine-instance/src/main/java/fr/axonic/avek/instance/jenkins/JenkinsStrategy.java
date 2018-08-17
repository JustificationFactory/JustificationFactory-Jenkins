package fr.axonic.avek.instance.jenkins;

import fr.axonic.avek.engine.strategy.ComputedStrategy;
import fr.axonic.avek.engine.strategy.Rationale;
import fr.axonic.avek.engine.strategy.UsageDomain;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.conclusion.Conclusion;

import java.util.List;

/**
 * Created by cduffau on 25/08/17.
 */
public class JenkinsStrategy extends ComputedStrategy{

    public JenkinsStrategy() {
        super();
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        return null;
    }

    public JenkinsStrategy(String name) {
        super(name, null,null);
    }
}
