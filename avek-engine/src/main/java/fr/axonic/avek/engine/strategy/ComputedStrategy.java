package fr.axonic.avek.engine.strategy;

import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.conclusion.Conclusion;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement
@XmlType(name = "computedStrategy")
public abstract class ComputedStrategy extends Strategy {

    public ComputedStrategy() {
    }

    public ComputedStrategy(String name, Rationale rationale, UsageDomain usageDomain) {
        super(name, rationale, usageDomain);
    }

    public abstract Conclusion createConclusion(List<Support> supportList);

}
