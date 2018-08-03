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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StepBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(StepBuilder.class);

    private final JustificationSystem justificationSystem;
    private final List<Support> knownSupports;

    public StepBuilder(JustificationSystem justificationSystem) {
        this.justificationSystem = justificationSystem;
        knownSupports = new ArrayList<>();
    }

    public void acknowledgeSupport(Support addedSupport) throws StepBuildingException, StrategyException {
        knownSupports.add(addedSupport);

        triggerStepBuilding();
    }

    private void triggerStepBuilding() throws StrategyException, StepBuildingException {
        PatternsBase patternsBase = justificationSystem.getPatternsBase();

        List<Pattern> patterns = patternsBase.getPossiblePatterns(knownSupports).stream()
                .map(patternsBase::getPattern)
                .collect(Collectors.toList());

        for (Pattern pattern : patterns) {
            List<Support> usefulSupports = pattern.filterUsefulEvidences(knownSupports);
            Conclusion associatedConclusion = createConclusion(usefulSupports);

            try {
                JustificationStep step = justificationSystem.constructStep(pattern, usefulSupports, associatedConclusion);

                // TODO What next with this step?
            } catch (WrongEvidenceException e) {
                LOGGER.error("Unexpected wrong support", e);
            }
        }

        if (!patterns.isEmpty()) {
            triggerStepBuilding();
        }
    }

    private Conclusion createConclusion(List<Support> usefulSupports) {
        return null; // TODO
    }
}
