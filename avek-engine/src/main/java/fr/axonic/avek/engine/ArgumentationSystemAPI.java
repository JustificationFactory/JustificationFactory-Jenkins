package fr.axonic.avek.engine;

import fr.axonic.avek.engine.constraint.PatternConstraintException;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.PatternsBase;
import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.engine.pattern.type.OutputType;
import fr.axonic.avek.engine.support.evidence.Hypothesis;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * Created by cduffau on 04/08/16.
 */
@XmlRootElement
@XmlSeeAlso(ArgumentationSystem.class)
public interface ArgumentationSystemAPI {

    Pattern getPattern(String patternId);

    void addPattern( Pattern pattern);

    List<SupportRole> getBaseEvidences();

    Step constructStep(Pattern pattern, List<SupportRole> evidences, Conclusion conclusion) throws StepBuildingException, WrongEvidenceException, StrategyException;

    List<Step> getSteps();

    PatternsBase getPatternsBase();

    OutputType getObjective();

    boolean validate();

    void resolveHypothesis(Step step, Hypothesis hypothesis, Support support) throws WrongEvidenceException, PatternConstraintException;

    void removeSteps();
}
