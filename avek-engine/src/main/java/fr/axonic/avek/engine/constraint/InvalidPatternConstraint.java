package fr.axonic.avek.engine.constraint;

/**
 * Created by cduffau on 15/03/17.
 */
public class InvalidPatternConstraint extends PatternConstraintException{
    public InvalidPatternConstraint() {
        super("");
    }
    public InvalidPatternConstraint(String message) {
        super(message);
    }
}
