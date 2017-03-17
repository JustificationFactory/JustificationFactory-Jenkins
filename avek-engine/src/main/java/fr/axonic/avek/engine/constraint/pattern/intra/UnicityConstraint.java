package fr.axonic.avek.engine.constraint.pattern.intra;

import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.Step;

import java.util.List;

/**
 * Created by cduffau on 09/03/17.
 */
public class UnicityConstraint extends IntraPatternConstraint {

    public UnicityConstraint(Pattern pattern) {
        super(pattern);
    }

    @Override
    public boolean verify(List<Step> steps) {
        return steps.stream().filter(step -> pattern.getId().equals(step.getPatternId())).count()<=1;
    }
}