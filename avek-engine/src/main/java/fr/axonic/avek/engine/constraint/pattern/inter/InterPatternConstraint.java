package fr.axonic.avek.engine.constraint.pattern.inter;

import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.constraint.PatternConstraint;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cduffau on 09/03/17.
 */
public abstract class InterPatternConstraint implements PatternConstraint {
    protected List<Pattern> patterns;

    public InterPatternConstraint(Pattern... patterns) {
        this.patterns = Arrays.asList(patterns);
    }
}
