package fr.axonic.avek.instance.strategy;

import fr.axonic.avek.engine.strategy.ComputedStrategy;
import fr.axonic.avek.engine.strategy.Rationale;
import fr.axonic.avek.engine.strategy.UsageDomain;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.conclusion.Conclusion;

import java.util.List;

/**
 * Created by cduffau on 04/08/16.
 */
public class EstablishEffectStrategy extends ComputedStrategy {

    public EstablishEffectStrategy(Rationale rationale, UsageDomain usageDomain) {
        super("Establish Effect",rationale, usageDomain);
    }

    public EstablishEffectStrategy() {
        this(null, null);
    }

    @Override
    public Conclusion createConclusion(List<Support> supportList) {
        return null;
    }
}
