package fr.axonic.avek.engine.kernel;

import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

public interface JustificationElement<T> {

    @XmlTransient
    boolean isTerminal();

    @XmlTransient
    List<T> conformsTo();
}
