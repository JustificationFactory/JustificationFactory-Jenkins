package fr.axonic.avek.engine.constraint.graph;

import fr.axonic.avek.engine.pattern.JustificationStep;
import fr.axonic.avek.engine.constraint.ArgumentationSystemConstraint;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.evidence.Evidence;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cduffau on 09/03/17.
 */
public class NoCycleConstraint implements ArgumentationSystemConstraint {

    private JustificationStep step;

    public NoCycleConstraint(JustificationStep step) {
        this.step = step;
    }

    @Override
    public boolean verify(List<JustificationStep> steps) {
        List<Support> supportRoles = step.getSupports().stream().filter(evidenceRole -> !(evidenceRole instanceof Evidence)).collect(Collectors.toList());
        if(supportRoles.size() == 0) {
            return true;
        }
        if(supportRoles.stream().anyMatch(evidenceRole -> evidenceRole.getId().equals(step.getConclusion().getId()))){
            return false;
        }
        return browsingToFindCycle(supportRoles, steps);
    }

    private boolean browsingToFindCycle(List<Support> supportRoles, List<JustificationStep> steps) {
        List<JustificationStep> stepsWithReuse = steps.stream()
                .filter(step -> step.getSupports().stream().
                        anyMatch(evidenceRole -> evidenceRole.getId().equals(step.getConclusion().getId())))
                .distinct().collect(Collectors.toList());

        if (stepsWithReuse.size() == 0) {
            return false;
        }
        boolean cycle = stepsWithReuse.stream().anyMatch(step1 ->
                supportRoles.stream().anyMatch(
                        evidenceRole -> step1.getConclusion().getId().equals(evidenceRole.getId())
                )
        );
        return cycle || browsingToFindCycle(supportRoles, stepsWithReuse);
    }
}
