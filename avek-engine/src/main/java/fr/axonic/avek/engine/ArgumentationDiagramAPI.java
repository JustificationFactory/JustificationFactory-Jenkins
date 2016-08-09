package fr.axonic.avek.engine;

import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.EvidenceRole;

import java.util.List;

/**
 * Created by cduffau on 04/08/16.
 */
public interface ArgumentationDiagramAPI {

    List<Pattern> getPossiblePatterns(List<EvidenceRole> evidenceRoles);

    List<EvidenceRole> getBaseEvidences();

    void constructStep(String patternId, List<EvidenceRole> evidences, Conclusion conclusion) throws StepBuildingException, WrongEvidenceException;

    List<Step> getSteps();
}
