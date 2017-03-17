package fr.axonic.avek.engine.constraint.pattern.inter;

import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.Step;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by cduffau on 09/03/17.
 */
public class NotCascadingConstraint extends InterPatternConstraint{

    public NotCascadingConstraint(Pattern pattern1, Pattern pattern2){
        super(pattern1,pattern2);
    }

    @Override
    public boolean verify(List<Step> steps) {
        Pattern pattern1 = patterns.get(0);
        Pattern pattern2 = patterns.get(1);
        List<Step> pattern1Steps=steps.stream().filter(step -> pattern1.getId().equals(step.getPatternId())).collect(Collectors.toList());
        List<Step> pattern2Steps=steps.stream().filter(step -> pattern2.getId().equals(step.getPatternId())).collect(Collectors.toList());

        Stream<Step> stepStream=pattern2Steps.stream()
                .filter(step -> step.getEvidences().stream().
                        anyMatch(evidenceRole -> pattern1Steps.stream()
                                .anyMatch(stepPattern -> evidenceRole.getSupport().getId().equals(stepPattern.getConclusion().getId()))))
                .distinct();
        return stepStream.count()==0;
    }
}
