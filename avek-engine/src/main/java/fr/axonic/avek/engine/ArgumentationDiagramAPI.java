package fr.axonic.avek.engine;

import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.EvidenceRole;

import java.util.List;

/**
 * Created by cduffau on 04/08/16.
 */
public interface ArgumentationDiagramAPI {

    List<String> getPossiblePatterns(List<EvidenceRole> evidenceRoles);

    Pattern getPattern(String patternId);

    List<EvidenceRole> getBaseEvidences();

    Step constructStep(String patternId, List<EvidenceRole> evidences, Conclusion conclusion) throws StepBuildingException, WrongEvidenceException, StrategyException;

    List<Step> getSteps();
}
