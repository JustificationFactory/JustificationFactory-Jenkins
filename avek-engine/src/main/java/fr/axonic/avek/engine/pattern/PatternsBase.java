package fr.axonic.avek.engine.pattern;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.constraint.ArgumentationSystemConstraint;
import fr.axonic.avek.engine.exception.WrongObjectiveException;
import fr.axonic.avek.engine.pattern.type.OutputType;
import fr.axonic.avek.engine.strategy.Strategy;
import fr.axonic.avek.engine.support.Support;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cduffau on 09/03/17.
 */
@XmlRootElement
@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type")
@XmlSeeAlso({ListPatternsBase.class, DiagramPatternsBase.class})
public abstract class PatternsBase {

    private PatternsBaseType patternsBaseType;

    public PatternsBase(PatternsBaseType patternsBaseType) {
        this.patternsBaseType=patternsBaseType;
    }
    public List<String> getPossiblePatterns(List<Support> supportRoles) {
        return getPatterns().stream().filter(pattern -> pattern.applicable(supportRoles)).map(Pattern::getId).collect(Collectors.toList());
    }

    @XmlElement
    @XmlElementWrapper
    public abstract List<Pattern> getPatterns();

    public abstract void setPatterns(List<Pattern> patterns);


    public abstract Pattern getPattern(String patternId);

    @Override
    public String toString() {
        return "PatternsBase{" +
                "type="+patternsBaseType.toString()+
                ", objective="+getObjective()+
                ", patterns=" + getPatterns() +'}';
    }

    @XmlElement
    public PatternsBaseType getPatternsBaseType() {
        return patternsBaseType;
    }

    public void setPatternsBaseType(PatternsBaseType patternsBaseType) {
        this.patternsBaseType = patternsBaseType;
    }


    @XmlElement
    public abstract OutputType getObjective();

}
