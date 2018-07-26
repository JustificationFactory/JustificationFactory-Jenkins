package fr.axonic.avek.engine.pattern;

import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.constraint.ArgumentationSystemConstraint;
import fr.axonic.avek.engine.exception.WrongObjectiveException;
import fr.axonic.avek.engine.pattern.type.OutputType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

public class ListPatternsBase extends PatternsBase{
    private List<Pattern> patterns;
    private List<ArgumentationSystemConstraint> constraints;
    private OutputType objective;

    public ListPatternsBase(List<Pattern> patterns, List<ArgumentationSystemConstraint> constraints) {
        super(PatternsBaseType.LIST_OF_PATTERNS);
        this.patterns=patterns;
        this.constraints=constraints;
    }

    public ListPatternsBase() {
        this(new ArrayList<>(),new ArrayList<>());
    }

    @Override
    public List<Pattern> getPatterns() {
        return patterns;
    }

    @Override
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

    @Override
    public Pattern getPattern(String patternId) {
        return patterns.stream().filter(pattern -> pattern.getId().equals(patternId)).collect(JustificationSystem.singletonCollector());
    }

    @Override
    public OutputType getObjective() {
        return objective;
    }

    public void setObjective(OutputType objective) throws WrongObjectiveException {
        if(objective!=null){
            if(getPatterns().stream().filter(pattern -> pattern.getOutputType().equals(objective)).count()>=1){
                this.objective = objective;
            }
            else{
                throw new WrongObjectiveException();
            }
        }
    }
}
