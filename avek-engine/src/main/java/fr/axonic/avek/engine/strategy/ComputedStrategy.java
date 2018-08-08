package fr.axonic.avek.engine.strategy;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "computedStrategy")
public abstract class ComputedStrategy extends Strategy {

    public ComputedStrategy() {
    }

    public ComputedStrategy(String name, Rationale rationale, UsageDomain usageDomain) {
        super(name, rationale, usageDomain);
    }

}
