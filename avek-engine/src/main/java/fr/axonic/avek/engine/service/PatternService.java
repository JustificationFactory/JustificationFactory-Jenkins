package fr.axonic.avek.engine.service;

import fr.axonic.avek.engine.Step;
import fr.axonic.avek.engine.conclusion.Conclusion;
import fr.axonic.avek.engine.evidence.EvidenceRole;

import java.util.List;

/**
 * Created by cduffau on 16/01/17.
 */
public interface PatternService {

    Step constructStep(int argumentationSystem, int pattern, List<EvidenceRole> evidences, Conclusion conclusion);

}
