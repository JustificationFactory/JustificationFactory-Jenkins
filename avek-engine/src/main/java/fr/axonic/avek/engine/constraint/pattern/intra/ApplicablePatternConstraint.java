package fr.axonic.avek.engine.constraint.pattern.intra;

import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.avek.engine.support.SupportRole;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cduffau on 21/03/17.
 */
public class ApplicablePatternConstraint {

    private Pattern pattern;

    public ApplicablePatternConstraint(Pattern pattern){
        this.pattern=pattern;
    }

    public boolean verify(List<SupportRole> supportRoles){
        List<InputType> evidenceRoleTypesUsed=new ArrayList<>();
        for (int i = 0; i < pattern.getInputTypes().size(); i++) {
            for (SupportRole supportRole : supportRoles) {
                if (pattern.getInputTypes().get(i).check(supportRole.getSupport()) && !evidenceRoleTypesUsed.contains(pattern.getInputTypes().get(i))) {
                    evidenceRoleTypesUsed.add(pattern.getInputTypes().get(i));
                    if (evidenceRoleTypesUsed.size() == pattern.getInputTypes().size()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
