package fr.axonic.avek.engine.constraint.pattern.intra;

import fr.axonic.avek.engine.Pattern;
import fr.axonic.avek.engine.Step;
import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.constraint.PatternConstraint;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by cduffau on 08/03/17.
 */
public abstract class IntraPatternConstraint implements PatternConstraint {
    protected Pattern pattern;

    public IntraPatternConstraint(Pattern pattern) {
        this.pattern=pattern;
    }

}
