package fr.axonic.avek.instance.strategy;

import fr.axonic.avek.engine.strategy.*;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Evidence;

import java.util.Map;

/**
 * Created by cduffau on 09/08/16.
 */
public class GeneralizeStrategy extends ComputedStrategy {

    public GeneralizeStrategy(Rationale rationale, UsageDomain usageDomain) {
        super("Generalize", rationale, usageDomain);
    }

    public GeneralizeStrategy() {
        this(null, null);
    }
}
