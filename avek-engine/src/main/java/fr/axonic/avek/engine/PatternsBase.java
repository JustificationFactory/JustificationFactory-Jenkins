package fr.axonic.avek.engine;

import fr.axonic.avek.engine.constraint.PatternConstraint;
import fr.axonic.avek.engine.evidence.EvidenceRole;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cduffau on 09/03/17.
 */
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

    @XmlElement
    @XmlElementWrapper
    public List<String> getPossiblePatterns(List<EvidenceRole> evidenceRoles) {
        return getPatterns().stream().filter(pattern -> pattern.applicable(evidenceRoles)).map(pattern -> pattern.getId()).collect(Collectors.toList());
    }
    public List<Pattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<Pattern> patterns) {
        this.patterns = patterns;
    }

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


}
