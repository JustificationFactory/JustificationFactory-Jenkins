package fr.axonic.avek.engine.kernel;

import javafx.util.Pair;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface JustificationDiagramAPI<T extends JustificationStepAPI> extends JustificationElement<JustificationDiagramAPI>{

    JustificationDiagramAPI derivatedFrom();

    void addStep(T step);

    List<T> getSteps();

    JustificationDiagramAPI copy();

    Map<ComparisonType, List<T>> compare(JustificationDiagramAPI justificationDiagramAPI);

    Pair<JustificationDiagramAPI,JustificationDiagramAPI> align(Map<ComparisonType, List<T>> stepsToKeep);

    @XmlTransient
    default List<Assertion> getUsedAssertions(){
        List<Assertion> assertions=new ArrayList<>();
        for(T step : getSteps()){
            List<Assertion> supports=new ArrayList<>(step.getSupports());
            supports.add(step.getConclusion());
            for(Assertion assertion : supports){
                if(!assertions.contains(assertion)){
                    assertions.add(assertion);
                }
            }

        }
        return assertions;
    }

    @Override
    @XmlTransient
    default boolean isTerminal(){
        return getSteps().stream().allMatch(JustificationStepAPI::isTerminal);
    }
}
