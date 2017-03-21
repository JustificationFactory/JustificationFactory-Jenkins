package fr.axonic.avek.engine.constraint.graph;

import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.engine.constraint.ArgumentationSystemConstraint;
import fr.axonic.avek.engine.support.evidence.Hypothesis;

import java.util.List;

/**
 * Created by cduffau on 10/03/17.
 */
public class NoHypothesisConstraint implements ArgumentationSystemConstraint {

    @Override
    public boolean verify(List<Step> steps) {
        return steps.stream().noneMatch(step -> step.getEvidences().stream().anyMatch(evidenceRole -> evidenceRole.getSupport() instanceof Hypothesis));
    }
}
