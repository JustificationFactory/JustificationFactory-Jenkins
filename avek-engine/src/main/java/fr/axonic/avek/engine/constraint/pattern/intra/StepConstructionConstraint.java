package fr.axonic.avek.engine.constraint.pattern.intra;

import fr.axonic.avek.engine.constraint.ArgumentationSystemConstraint;
import fr.axonic.avek.engine.constraint.StepConstraint;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.SupportRole;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by cduffau on 21/03/17.
 */
public class StepConstructionConstraint implements StepConstraint, ArgumentationSystemConstraint{

    private Pattern pattern;

    public StepConstructionConstraint(Pattern pattern){
        this.pattern=pattern;
    }

    @Override
    public boolean verify(Step step){
        if(!pattern.getOutputType().check(step.getConclusion())){
            return false;
        }
        if(pattern.getInputTypes().size()!=step.getEvidences().size()) {
         return false;
        }
        List<Support> supports=SupportRole.translateToSupport(step.getEvidences());
        return pattern.getInputTypes().stream().allMatch(inputType -> {Optional<Support> s=supports.stream().filter(inputType::check).findAny(); supports.remove(s.get()); return s.isPresent();});
    }

    @Override
    public boolean verify(List<Step> steps) {
        List<Step> patternSteps=steps.stream().filter(step -> step.getPatternId().equals(pattern.getId())).distinct().collect(Collectors.toList());
        return patternSteps.stream().allMatch(this::verify);
    }
}
