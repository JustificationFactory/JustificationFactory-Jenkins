package fr.axonic.avek.engine.constraint.pattern.inter;

import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.engine.constraint.PatternConstraint;
import fr.axonic.avek.engine.support.evidence.Hypothesis;

import java.util.List;

/**
 * Created by cduffau on 10/03/17.
 */
public class NoHypothesisConstraint implements PatternConstraint{

    @Override
    public boolean verify(List<Step> steps) {
        return steps.stream().noneMatch(step -> step.getEvidences().stream().anyMatch(evidenceRole -> evidenceRole.getSupport() instanceof Hypothesis));
    }
}
