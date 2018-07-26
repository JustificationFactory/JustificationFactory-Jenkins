package fr.axonic.avek.engine.diagram;

import fr.axonic.avek.engine.kernel.ComparisonType;
import fr.axonic.avek.engine.kernel.JustificationDiagramAPI;
import fr.axonic.avek.engine.pattern.Pattern;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JustificationPatternDiagram implements JustificationDiagramAPI<Pattern> {

    private List<Pattern> steps;

    public JustificationPatternDiagram(){
        steps=new ArrayList<>();
    }

    public JustificationPatternDiagram(List<Pattern> steps) {
        this.steps = steps;
    }

    @Override
    public JustificationDiagramAPI derivatedFrom() {
        return null;
    }

    @Override
    public void addStep(Pattern step) {
        steps.add(step);
    }

    @Override
    public List<Pattern> getSteps() {
        return steps;
    }

    @Override
    public JustificationDiagramAPI copy() {
        try {
            return (JustificationDiagramAPI) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<ComparisonType, List<Pattern>> compare(JustificationDiagramAPI justificationDiagramAPI) {
        return null;
    }

    @Override
    public Pair<JustificationDiagramAPI, JustificationDiagramAPI> align(Map<ComparisonType, List<Pattern>> stepsToKeep) {
        return null;
    }


}
