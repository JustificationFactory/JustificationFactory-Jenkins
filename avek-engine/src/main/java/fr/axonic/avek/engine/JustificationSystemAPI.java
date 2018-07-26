package fr.axonic.avek.engine;

import fr.axonic.avek.engine.constraint.PatternConstraintException;
import fr.axonic.avek.engine.kernel.JustificationDiagramAPI;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.PatternsBase;
import fr.axonic.avek.engine.pattern.JustificationStep;
import fr.axonic.avek.engine.pattern.type.OutputType;
import fr.axonic.avek.engine.support.evidence.Hypothesis;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * Created by cduffau on 04/08/16.
 */
@XmlRootElement
@XmlSeeAlso(JustificationSystem.class)
public interface JustificationSystemAPI {

    Pattern getPattern(String patternId);

    void addPattern( Pattern pattern);

    List<SupportRole> getBaseEvidences();

    JustificationStep constructStep(Pattern pattern, List<SupportRole> evidences, Conclusion conclusion) throws StepBuildingException, WrongEvidenceException, StrategyException;

    JustificationDiagramAPI getJustificationDiagram();

    PatternsBase getPatternsBase();

    OutputType getObjective();

    boolean validate();

    void resolveHypothesis(JustificationStep step, Hypothesis hypothesis, Support support) throws WrongEvidenceException, PatternConstraintException;

    void removeSteps();
}
