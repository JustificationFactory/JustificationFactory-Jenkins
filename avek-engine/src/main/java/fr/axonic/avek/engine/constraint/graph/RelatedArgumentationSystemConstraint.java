package fr.axonic.avek.engine.constraint.graph;

import fr.axonic.avek.engine.constraint.ArgumentationSystemConstraint;
import fr.axonic.avek.engine.pattern.JustificationStep;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cduffau on 21/03/17.
 */
public class RelatedArgumentationSystemConstraint implements ArgumentationSystemConstraint {
    @Override
    public boolean verify(List<JustificationStep> steps) {
        if(steps.size()==1){
            return true;
        }
        List<JustificationStep> linkedSteps=steps.stream().filter(step -> step.getSupports().stream().anyMatch(evidenceRole -> steps.stream()
                .anyMatch(stepPattern -> evidenceRole.getSupport().getId().equals(stepPattern.getConclusion().getId()))) || steps.stream().anyMatch(step1 -> step1.getSupports().stream().anyMatch(supportRole -> step.getConclusion().getId().equals(supportRole.getSupport().getId())))).distinct().collect(Collectors.toList());
        return linkedSteps.size()==steps.size();
    }
}
