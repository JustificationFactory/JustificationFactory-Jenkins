package fr.axonic.avek.engine.strategy;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.axonic.avek.instance.strategy.TreatStrategy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({HumanStrategy.class, ComputedStrategy.class})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Strategy)) return false;

        Strategy strategy = (Strategy) o;

        if (name != null ? !name.equals(strategy.name) : strategy.name != null) return false;
        if (rationale != null ? !rationale.equals(strategy.rationale) : strategy.rationale != null) return false;
        return usageDomain != null ? usageDomain.equals(strategy.usageDomain) : strategy.usageDomain == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (rationale != null ? rationale.hashCode() : 0);
        result = 31 * result + (usageDomain != null ? usageDomain.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Strategy{" +
                "name='" + name + '\'' +
                ", rationale=" + rationale +
                ", usageDomain=" + usageDomain +
                '}';
    }
}
