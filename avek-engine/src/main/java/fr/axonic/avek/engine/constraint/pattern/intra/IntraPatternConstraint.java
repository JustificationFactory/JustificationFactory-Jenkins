package fr.axonic.avek.engine.constraint.pattern.intra;

import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.constraint.PatternConstraint;

/**
 * Created by cduffau on 08/03/17.
 */
public abstract class IntraPatternConstraint implements PatternConstraint {
    protected Pattern pattern;

    public IntraPatternConstraint(Pattern pattern) {
        this.pattern=pattern;
    }

}
