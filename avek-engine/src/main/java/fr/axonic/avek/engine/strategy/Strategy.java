package fr.axonic.avek.engine.strategy;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({HumanStrategy.class, ComputedStrategy.class})
@JsonDeserialize(as=HumanStrategy.class)
public abstract class Strategy {

    private String name;
    private Rationale rationale;
    private UsageDomain usageDomain;

    public Strategy() {
    }

    public Strategy(String name, Rationale rationale, UsageDomain usageDomain) {
        this.name=name;
        this.rationale = rationale;
        this.usageDomain = usageDomain;
    }

    @XmlElement
    public UsageDomain getUsageDomain() {
        return usageDomain;
    }

    @XmlElement
    public Rationale getRationale() {
        return rationale;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
