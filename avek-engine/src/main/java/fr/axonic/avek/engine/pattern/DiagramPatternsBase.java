package fr.axonic.avek.engine.pattern;

import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.diagram.JustificationPatternDiagram;
import fr.axonic.avek.engine.pattern.type.OutputType;

import java.util.List;

public class DiagramPatternsBase extends PatternsBase {

    public JustificationPatternDiagram jpd;

    public DiagramPatternsBase(JustificationPatternDiagram jpd) {
        super(PatternsBaseType.PATTERN_DIAGRAM);
        this.jpd=jpd;
    }

    @Override
    public List<Pattern> getPatterns() {
        return jpd.getSteps();
    }

    @Override
    public void setPatterns(List<Pattern> patterns) {
        jpd=new JustificationPatternDiagram(patterns);
    }

    @Override
    public Pattern getPattern(String patternId) {
        return jpd.getSteps().stream().filter(pattern -> pattern.getId().equals(patternId)).collect(JustificationSystem.singletonCollector());
    }

    @Override
    public OutputType getObjective() {
        return jpd.getSteps().get(jpd.getSteps().size()-1).getOutputType();
    }
}
