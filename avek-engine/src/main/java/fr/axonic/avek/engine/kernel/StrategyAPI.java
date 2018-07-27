package fr.axonic.avek.engine.kernel;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.axonic.avek.engine.strategy.Strategy;
import fr.axonic.avek.engine.strategy.UsageDomain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type")
@XmlSeeAlso({Strategy.class})
public abstract class StrategyAPI {

    private String name;


    public StrategyAPI(String name) {
        this.name=name;
    }

    public StrategyAPI() {

    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
