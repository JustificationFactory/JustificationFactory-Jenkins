package fr.axonic.avek.engine.constraint;

import fr.axonic.avek.engine.pattern.JustificationStep;

import java.util.List;

/**
 * Created by cduffau on 09/03/17.
 */
public interface ArgumentationSystemConstraint {

    boolean verify(List<JustificationStep> steps);

}
