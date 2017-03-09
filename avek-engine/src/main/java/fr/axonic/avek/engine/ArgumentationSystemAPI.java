package fr.axonic.avek.engine;

import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.EvidenceRole;

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

    List<EvidenceRole> getBaseEvidences();

    Step constructStep(Pattern pattern, List<EvidenceRole> evidences, Conclusion conclusion) throws StepBuildingException, WrongEvidenceException, StrategyException;

    List<Step> getSteps();

    PatternsBase getPatternsBase();

    ConclusionType getObjective();

    boolean validate();
}
