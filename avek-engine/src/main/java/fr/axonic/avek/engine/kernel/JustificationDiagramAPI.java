package fr.axonic.avek.engine.kernel;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface JustificationDiagramAPI<T extends JustificationStepAPI> {

    JustificationDiagramAPI derivatedFrom();

    void addStep(T step);

    List<T> getSteps();

    JustificationDiagramAPI copy();

    Map<ComparisonType, List<T>> compare(JustificationDiagramAPI justificationDiagramAPI);

    Pair<JustificationDiagramAPI,JustificationDiagramAPI> align(Map<ComparisonType, List<T>> stepsToKeep);
}
