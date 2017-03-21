package fr.axonic.avek.engine.constraint.pattern.intra;

import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.Step;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.avek.engine.support.Support;
import fr.axonic.avek.engine.support.SupportRole;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cduffau on 21/03/17.
 */
public class StepConstructionConstraint {

    private Pattern pattern;

    public StepConstructionConstraint(Pattern pattern){
        this.pattern=pattern;
    }

    public boolean verify(Step step){
        if(!pattern.getOutputType().check(step.getConclusion())){
            return false;
        }
        if(pattern.getInputTypes().size()==step.getEvidences().size()){
            List<Support> supports=SupportRole.translateToEvidence(step.getEvidences());
            for(int i = 0; i< pattern.getInputTypes().size(); i++){
                InputType roleType= pattern.getInputTypes().get(i);
                if(!roleType.check(supports.get(i))){
                    return false;
                }
            }
        }


        return true;
    }
}
