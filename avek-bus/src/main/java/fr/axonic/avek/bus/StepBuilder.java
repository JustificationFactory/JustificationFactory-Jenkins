package fr.axonic.avek.bus;

import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.exception.StepBuildingException;
import fr.axonic.avek.engine.exception.StrategyException;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.JustificationStep;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.PatternsBase;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.instance.redmine.RedmineConclusion;
import fr.axonic.avek.instance.redmine.RedmineDocumentEvidence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StepBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(StepBuilder.class);

    private final List<JustificationSystem> justificationSystems;
    private final List<Support> knownSupports;

    public StepBuilder(List<JustificationSystem> justificationSystems) {
        this.justificationSystems = justificationSystems;
        knownSupports = new ArrayList<>();
    }

    public void acknowledgeSupport(Support addedSupport) throws StepBuildingException, StrategyException {
        knownSupports.add(addedSupport);

        triggerStepsBuilding();
    }

    private void triggerStepsBuilding() throws StrategyException, StepBuildingException {
        for (JustificationSystem system : justificationSystems) {
            triggerOneSystemStepsBuilding(system);
        }
    }

    private void triggerOneSystemStepsBuilding(JustificationSystem justificationSystem) throws StrategyException, StepBuildingException {
        PatternsBase patternsBase = justificationSystem.getPatternsBase();

        List<Pattern> patterns = patternsBase.getPossiblePatterns(knownSupports).stream()
                .map(patternsBase::getPattern)
                .collect(Collectors.toList());

        for (Pattern pattern : patterns) {
            List<Support> usefulSupports = pattern.filterUsefulEvidences(knownSupports);
            Conclusion associatedConclusion = createConclusion(usefulSupports);

            try {
                JustificationStep step = justificationSystem.constructStep(pattern, usefulSupports, associatedConclusion);

                // TODO What is next with this step?
            } catch (WrongEvidenceException e) {
                LOGGER.error("Unexpected wrong support", e);
            }
        }

        if (!patterns.isEmpty()) {
            triggerOneSystemStepsBuilding(justificationSystem);
        }
    }

    private Conclusion createConclusion(List<Support> usefulSupports) {
        if (usefulSupports.isEmpty()) {
            return null;
        }

        Optional<RedmineDocumentEvidence> evidence = usefulSupports.stream()
                .filter(s -> s instanceof RedmineDocumentEvidence)
                .map(s -> (RedmineDocumentEvidence) s)
                .findFirst();

        return evidence.map(RedmineConclusion::create).orElse(null);
    }
}
