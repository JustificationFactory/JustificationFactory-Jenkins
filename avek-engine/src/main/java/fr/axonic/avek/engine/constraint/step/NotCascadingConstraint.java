package fr.axonic.avek.engine.constraint.step;

import fr.axonic.avek.engine.constraint.PatternConstraint;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.JustificationStep;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by cduffau on 09/03/17.
 */
public class NotCascadingConstraint extends PatternConstraint {

    public NotCascadingConstraint(Pattern pattern1, Pattern pattern2){
        super(pattern1,pattern2);
    }

    @Override
    public boolean verify(List<JustificationStep> steps) {
        Pattern pattern1 = patterns.get(0);
        Pattern pattern2 = patterns.get(1);
        List<JustificationStep> pattern1Steps=steps.stream().filter(step -> pattern1.getId().equals(step.getPatternId())).collect(Collectors.toList());
        List<JustificationStep> pattern2Steps=steps.stream().filter(step -> pattern2.getId().equals(step.getPatternId())).collect(Collectors.toList());

        Stream<JustificationStep> stepStream=pattern2Steps.stream()
                .filter(step -> step.getSupports().stream().
                        anyMatch(evidenceRole -> pattern1Steps.stream()
                                .anyMatch(stepPattern -> evidenceRole.getId().equals(stepPattern.getConclusion().getId()))))
                .distinct();
        return stepStream.count()==0;
    }
}
