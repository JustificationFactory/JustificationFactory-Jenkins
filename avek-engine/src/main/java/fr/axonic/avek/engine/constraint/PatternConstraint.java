package fr.axonic.avek.engine.constraint;

import fr.axonic.avek.engine.pattern.Pattern;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cduffau on 09/03/17.
 */
public abstract class PatternConstraint implements ArgumentationSystemConstraint {
    protected List<Pattern> patterns;

    public PatternConstraint(Pattern... patterns) {
        this.patterns = Arrays.asList(patterns);
    }
}
