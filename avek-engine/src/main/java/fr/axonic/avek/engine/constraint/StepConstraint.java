package fr.axonic.avek.engine.constraint;

import fr.axonic.avek.engine.pattern.Step;

/**
 * Created by cduffau on 24/03/17.
 */
public interface StepConstraint {

    boolean verify(Step step);
}
