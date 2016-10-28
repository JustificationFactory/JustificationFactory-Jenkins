package fr.axonic.avek.engine.instance.strategy;

import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.Evidence;
import fr.axonic.avek.engine.strategy.*;

import java.util.Map;

/**
 * Created by cduffau on 04/08/16.
 */
public class EstablishEffectStrategy extends HumanStrategy {

    public EstablishEffectStrategy(Rationale rationale, UsageDomain usageDomain) {
        super("Establish Effect",rationale, usageDomain,Role.JUNIOR_EXPERT);
    }

    @Override
    public boolean check(Map<String, Evidence> evidenceRoles, Conclusion conclusion) {
        return true;
    }
}
