package fr.axonic.avek.engine.instance.strategy;

import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.Evidence;
import fr.axonic.avek.engine.strategy.HumanStrategy;
import fr.axonic.avek.engine.strategy.Strategy;

import java.util.Map;

/**
 * Created by cduffau on 04/08/16.
 */
public class EstablishEffectStrategy extends HumanStrategy {
    @Override
    public boolean check(Map<String, Evidence> evidenceRoles, Conclusion conclusion) {
        return true;
    }
}
