package fr.axonic.avek.engine.constraint.pattern.intra;

import fr.axonic.avek.engine.constraint.InvalidPatternConstraint;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.type.SupportType;
import fr.axonic.avek.engine.strategy.Actor;
import fr.axonic.avek.engine.strategy.HumanStrategy;
import fr.axonic.avek.engine.strategy.Role;

/**
 * Created by cduffau on 22/03/17.
 */
public class ActorTypePatternConstraint extends SupportTypePatternConstraint<Actor> {


    public ActorTypePatternConstraint(Pattern pattern, SupportType<Actor> supportType, Role roleMinimum) throws InvalidPatternConstraint {
        super(pattern, supportType, step -> step.getStrategy() instanceof HumanStrategy && step.getSupports().stream().allMatch(supportRole -> !supportType.check(supportRole.getSupport()) || ((Actor)supportRole.getSupport()).getRole().ordinal()<=roleMinimum.ordinal()));
    }
}
