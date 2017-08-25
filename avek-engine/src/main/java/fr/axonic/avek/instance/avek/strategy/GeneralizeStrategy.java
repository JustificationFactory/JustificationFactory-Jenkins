package fr.axonic.avek.instance.avek.strategy;

import fr.axonic.avek.engine.strategy.*;

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
