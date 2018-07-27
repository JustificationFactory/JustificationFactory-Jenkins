package fr.axonic.avek.engine.kernel;

import javafx.util.Pair;

import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import java.util.Map;

public interface JustificationDiagramAPI<T extends JustificationStepAPI> extends JustificationElement<JustificationDiagramAPI>{

    JustificationDiagramAPI derivatedFrom();

    void addStep(T step);

    List<T> getSteps();

    JustificationDiagramAPI copy();

    Map<ComparisonType, List<T>> compare(JustificationDiagramAPI justificationDiagramAPI);

    Pair<JustificationDiagramAPI,JustificationDiagramAPI> align(Map<ComparisonType, List<T>> stepsToKeep);

    @Override
    @XmlTransient
    default boolean isTerminal(){
        return getSteps().stream().allMatch(JustificationStepAPI::isTerminal);
    }
}
