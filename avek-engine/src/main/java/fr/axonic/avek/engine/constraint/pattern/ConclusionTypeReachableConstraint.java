package fr.axonic.avek.engine.constraint.pattern;

import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.PatternsBase;
import fr.axonic.avek.engine.pattern.type.OutputType;
import fr.axonic.avek.engine.pattern.type.InputType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cduffau on 15/03/17.
 */
public class ConclusionTypeReachableConstraint {
    private OutputType conclusionType;

    public ConclusionTypeReachableConstraint(OutputType conclusionType){
        this.conclusionType=conclusionType;
    }
    
    public boolean verify(PatternsBase patterns) {
        List<Pattern> patternsWithConclusionType=patterns.getPatterns().stream().filter(pattern -> pattern.getOutputType().equals(conclusionType)).collect(Collectors.toList());
        List<Pattern> patternsWithOnlyEvidences=patterns.getPatterns().stream().filter(pattern -> pattern.getInputTypes().stream().allMatch(InputType::isPrimitiveInputType)).collect(Collectors.toList());
        if(patternsWithOnlyEvidences.size()==0 || patternsWithConclusionType.size()==0){
            return false;
        }

        return browsingToFindReachablePatterns(patterns.getPatterns(),patternsWithOnlyEvidences, patternsWithConclusionType);
    }

    private boolean browsingToFindReachablePatterns(List<Pattern> allPatterns, List<Pattern> patternsReachable, List<Pattern> patternsWithGoodConclusionType) {
        List<Pattern> newPatternReachable = allPatterns.stream().filter(pattern -> pattern.getInputTypes().stream().allMatch(evidenceRoleType -> (evidenceRoleType.isPrimitiveInputType() || patternsReachable.stream().anyMatch(pattern1 -> evidenceRoleType.getType().equals(pattern1.getOutputType().getType()))))).collect(Collectors.toList());
        patternsReachable.addAll(newPatternReachable);
        long found = patternsReachable.stream().filter(patternsWithGoodConclusionType::contains).count();
        return found > 0 || browsingToFindReachablePatterns(allPatterns, patternsReachable, patternsWithGoodConclusionType);
    }
}
