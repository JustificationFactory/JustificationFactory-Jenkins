package fr.axonic.avek.engine.pattern;

import fr.axonic.avek.engine.ArgumentationSystem;
import fr.axonic.avek.engine.constraint.PatternConstraint;
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

    private List<Pattern> patterns;
    private List<PatternConstraint> constraints;

    public PatternsBase(List<Pattern> patterns, List<PatternConstraint> constraints) {
        this.patterns = patterns;
        this.constraints = constraints;
    }

    public PatternsBase() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public PatternsBase(List<Pattern> patterns) {
        this(patterns, new ArrayList<>());
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
    public List<PatternConstraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<PatternConstraint> constraints) {
        this.constraints = constraints;
    }

    public void addConstraint(PatternConstraint patternConstraint){
        this.constraints.add(patternConstraint);
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
                "patterns=" + patterns +
                ", constraints=" + constraints +
                '}';
    }
}
