package fr.axonic.avek.engine.constraint.pattern.intra;

import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.avek.engine.support.Support;

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

    public boolean verify(List<Support> supports){
        List<InputType> evidenceRoleTypesUsed=new ArrayList<>();
        for (int i = 0; i < pattern.getInputTypes().size(); i++) {
            for (Support support : supports) {
                if (pattern.getInputTypes().get(i).check(support) && !evidenceRoleTypesUsed.contains(pattern.getInputTypes().get(i))) {
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
