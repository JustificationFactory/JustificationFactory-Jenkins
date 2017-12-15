package fr.axonic.avek;

import fr.axonic.avek.engine.strategy.*;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Evidence;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlRootElement
public enum ArtifactType {
    SUPPORT(Evidence.class,Conclusion.class),
    EVIDENCE(Evidence.class),
    CONCLUSION(Conclusion.class),
    STRATEGY(HumanStrategy.class, ComputedStrategy.class),
    COMPUTED_STRATEGY(ComputedStrategy.class),
    HUMAN_STRATEGY(HumanStrategy.class),
    RATIONALE(Rationale.class),
    USAGE_DOMAIN(UsageDomain.class),
    ACTOR(Actor.class);

    private List<Class> classes;
    ArtifactType(Class ... supportClass) {
        classes=Arrays.asList(supportClass);
    }

    ArtifactType() {
    }

    @XmlTransient
    public List<Class> getClasses() {
        return classes;
    }
}
