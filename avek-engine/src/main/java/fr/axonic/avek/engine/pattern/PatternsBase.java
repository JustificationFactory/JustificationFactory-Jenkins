package fr.axonic.avek.engine.pattern;

import fr.axonic.avek.engine.ArgumentationSystem;
import fr.axonic.avek.engine.constraint.ArgumentationSystemConstraint;
import fr.axonic.avek.engine.support.SupportRole;

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
public class PatternsBase {

    private PatternsBaseType patternsBaseType;
    private List<Pattern> patterns;
    private List<ArgumentationSystemConstraint> constraints;

    public PatternsBase(PatternsBaseType patternsBaseType,List<Pattern> patterns, List<ArgumentationSystemConstraint> constraints) {
        this.patterns = patterns;
        this.constraints = constraints;
        this.patternsBaseType=patternsBaseType;
    }

    public PatternsBase() {
        this(PatternsBaseType.LIST_OF_PATTERNS,new ArrayList<>(), new ArrayList<>());
    }

    public PatternsBase(List<Pattern> patterns) {
        this(PatternsBaseType.LIST_OF_PATTERNS,patterns, new ArrayList<>());
    }

    public List<String> getPossiblePatterns(List<SupportRole> supportRoles) {
        return getPatterns().stream().filter(pattern -> pattern.applicable(supportRoles)).map(Pattern::getId).collect(Collectors.toList());
    }

    @XmlElement
    @XmlElementWrapper
    public List<Pattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<Pattern> patterns) {
        this.patterns = patterns;
    }

    @XmlTransient
    public List<ArgumentationSystemConstraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<ArgumentationSystemConstraint> constraints) {
        this.constraints = constraints;
    }

    public void addConstraint(ArgumentationSystemConstraint argumentationSystemConstraint){
        this.constraints.add(argumentationSystemConstraint);
    }
    public void addPattern(Pattern pattern){
        this.patterns.add(pattern);
    }

    public Pattern getPattern(String patternId) {
        return patterns.stream().filter(pattern -> pattern.getId().equals(patternId)).collect(ArgumentationSystem.singletonCollector());
    }

    @Override
    public String toString() {
        return "PatternsBase{" +
                "type"+patternsBaseType.toString()+
                ", patterns=" + patterns +
                ", constraints=" + constraints +
                '}';
    }

    @XmlElement
    public PatternsBaseType getPatternsBaseType() {
        return patternsBaseType;
    }

    public void setPatternsBaseType(PatternsBaseType patternsBaseType) {
        this.patternsBaseType = patternsBaseType;
    }
}
