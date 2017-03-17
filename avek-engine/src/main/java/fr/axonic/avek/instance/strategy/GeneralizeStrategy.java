package fr.axonic.avek.instance.strategy;

import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.engine.strategy.HumanStrategy;
import fr.axonic.avek.engine.strategy.Rationale;
import fr.axonic.avek.engine.strategy.Role;
import fr.axonic.avek.engine.strategy.UsageDomain;

import java.util.Map;

/**
 * Created by cduffau on 09/08/16.
 */
public class GeneralizeStrategy extends HumanStrategy{

    public GeneralizeStrategy(Rationale rationale, UsageDomain usageDomain) {
        super("Generalize",rationale, usageDomain, Role.INTERMEDIATE_EXPERT);
    }

    @Override
    public boolean check(Map<String, Evidence> evidenceRoles, Conclusion conclusion) {
        return true;
    }
}
