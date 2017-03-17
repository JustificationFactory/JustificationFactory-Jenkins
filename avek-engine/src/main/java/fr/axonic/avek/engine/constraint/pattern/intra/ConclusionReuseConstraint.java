package fr.axonic.avek.engine.constraint.pattern.intra;

import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.Step;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by cduffau on 09/03/17.
 */
public class ConclusionReuseConstraint extends IntraPatternConstraint {

    public ConclusionReuseConstraint(Pattern pattern) {
        super(pattern);
    }

    @Override
    public boolean verify(List<Step> steps) {
        List<Step> patternSteps=steps.stream().filter(step -> pattern.getId().equals(step.getPatternId())).collect(Collectors.toList());
        Stream<Step> stepStream=steps.stream()
                .filter(step -> step.getEvidences().stream().
                        anyMatch(evidenceRole -> patternSteps.stream()
                                .anyMatch(stepPattern -> evidenceRole.getSupport().getId().equals(stepPattern.getConclusion().getId())))
                        )
                .distinct();
        return stepStream.count()>=patternSteps.size();
    }
}
