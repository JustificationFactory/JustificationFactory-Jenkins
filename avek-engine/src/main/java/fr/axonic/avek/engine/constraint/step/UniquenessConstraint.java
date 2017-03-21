package fr.axonic.avek.engine.constraint.step;

import fr.axonic.avek.engine.constraint.PatternConstraint;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.Step;

import java.util.List;

/**
 * Created by cduffau on 09/03/17.
 */
public class UniquenessConstraint extends PatternConstraint {

    public UniquenessConstraint(Pattern pattern) {
        super(pattern);
    }

    @Override
    public boolean verify(List<Step> steps) {
        return steps.stream().filter(step -> patterns.get(0).getId().equals(step.getPatternId())).count()<=1;
    }
}
