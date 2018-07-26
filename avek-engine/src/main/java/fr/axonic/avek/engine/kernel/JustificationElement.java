package fr.axonic.avek.engine.kernel;

import java.util.List;

public interface JustificationElement<T> {

    boolean isTerminal();

    List<T> conformsTo();
}
