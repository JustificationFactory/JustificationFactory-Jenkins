package fr.axonic.avek.gui.components.parameters.leaves;

import fr.axonic.base.engine.AVar;

/**
 * Created by Nathaël N on 21/07/16.
 */
public class SimpleParameter<T> extends SensitiveParameter<T> {
    public SimpleParameter(int level, AVar<T> value) {
        super(level, value);
    }
}
