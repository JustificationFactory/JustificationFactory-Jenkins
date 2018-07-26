package fr.axonic.avek.engine.pattern;

import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.constraint.ArgumentationSystemConstraint;
import fr.axonic.avek.engine.exception.WrongObjectiveException;
import fr.axonic.avek.engine.pattern.type.OutputType;
import fr.axonic.avek.engine.support.Support;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cduffau on 09/03/17.
 */
@XmlRootElement
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
