package fr.axonic.avek.instance.strategy;

import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.engine.strategy.*;

import java.util.Map;

/**
 * Created by cduffau on 04/08/16.
 */
public class EstablishEffectStrategy extends ComputedStrategy {

    public EstablishEffectStrategy(Rationale rationale, UsageDomain usageDomain) {
        super("Establish Effect",rationale, usageDomain);
    }


    @Override
    public Conclusion createConclusion(Map<String, Evidence> evidenceRoles) {
        return null;
    }
}
