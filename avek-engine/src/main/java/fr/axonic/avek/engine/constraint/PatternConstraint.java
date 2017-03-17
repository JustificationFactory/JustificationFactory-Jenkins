package fr.axonic.avek.engine.constraint;

import fr.axonic.avek.engine.pattern.Step;

import java.util.List;

/**
 * Created by cduffau on 09/03/17.
 */
public interface PatternConstraint {

    boolean verify(List<Step> o);

}
