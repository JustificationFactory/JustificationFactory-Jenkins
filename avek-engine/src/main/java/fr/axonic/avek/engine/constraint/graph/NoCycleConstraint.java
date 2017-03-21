package fr.axonic.avek.engine.constraint.graph;

import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.engine.constraint.ArgumentationSystemConstraint;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.engine.support.SupportRole;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cduffau on 09/03/17.
 */
public class NoCycleConstraint implements ArgumentationSystemConstraint {

    private Step step;

    public NoCycleConstraint(Step step) {
        this.step = step;
    }

    @Override
    public boolean verify(List<Step> steps) {
        List<SupportRole> supportRoles = step.getEvidences().stream().filter(evidenceRole -> !(evidenceRole.getSupport() instanceof Evidence)).collect(Collectors.toList());
        if(supportRoles.size() == 0) {
            return true;
        }
        if(supportRoles.stream().anyMatch(evidenceRole -> evidenceRole.getSupport().getId().equals(step.getConclusion().getId()))){
            return false;
        }
        return browsingToFindCycle(supportRoles, steps);
    }

    private boolean browsingToFindCycle(List<SupportRole> supportRoles, List<Step> steps) {
        List<Step> stepsWithReuse = steps.stream()
                .filter(step -> step.getEvidences().stream().
                        anyMatch(evidenceRole -> evidenceRole.getSupport().getId().equals(step.getConclusion().getId())))
                .distinct().collect(Collectors.toList());

        if (stepsWithReuse.size() == 0) {
            return false;
        }
        boolean cycle = stepsWithReuse.stream().anyMatch(step1 ->
                supportRoles.stream().anyMatch(
                        evidenceRole -> step1.getConclusion().getId().equals(evidenceRole.getSupport().getId())
                )
        );
        return cycle || browsingToFindCycle(supportRoles, stepsWithReuse);
    }
}
