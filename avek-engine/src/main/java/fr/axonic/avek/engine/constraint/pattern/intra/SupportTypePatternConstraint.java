package fr.axonic.avek.engine.constraint.pattern.intra;

import fr.axonic.avek.engine.constraint.ArgumentationSystemConstraint;
import fr.axonic.avek.engine.constraint.InvalidPatternConstraint;
import fr.axonic.avek.engine.constraint.StepConstraint;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.engine.pattern.type.SupportType;
import fr.axonic.avek.engine.support.Support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by cduffau on 22/03/17.
 */
public class SupportTypePatternConstraint<T extends Support> implements ArgumentationSystemConstraint,StepConstraint{

    protected Pattern pattern;
    protected SupportType<T> supportType;
    protected Predicate<Step> predicate;

    public SupportTypePatternConstraint(Pattern pattern, SupportType<T> supportType, Predicate<Step> predicate) throws InvalidPatternConstraint {
        this.pattern = pattern;
        this.supportType = supportType;
        this.predicate=predicate;
        if(pattern.getInputTypes().stream().noneMatch(inputType -> inputType.equals(supportType)) && !pattern.getOutputType().equals(supportType)){
            throw new InvalidPatternConstraint("No "+ supportType +" in "+pattern);
        }
    }

    @Override
    public boolean verify(List<Step> steps){
        List<Step> filteredSteps=steps.stream().filter(step -> step.getPatternId().equals(pattern.getId()) && step.getEvidences().stream().anyMatch(supportRole -> supportType.check(supportRole.getSupport()))).collect(Collectors.toList());
        return filteredSteps.stream().allMatch(predicate);
    }

    @Override
    public boolean verify(Step step){
        return verify(Collections.singletonList(step));
    }
}
