package fr.axonic.avek.instance.redmine;

import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.constraint.pattern.intra.SameArtifactVersionApplicablePatternConstraint;
import fr.axonic.avek.engine.diagram.JustificationPatternDiagram;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.DiagramPatternsBase;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.avek.engine.pattern.type.OutputType;
import fr.axonic.avek.engine.pattern.type.Type;
import fr.axonic.avek.engine.strategy.Strategy;
import fr.axonic.validation.exception.VerificationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedmineJustificationSystem extends JustificationSystem<DiagramPatternsBase> {

    public RedmineJustificationSystem() throws VerificationException, WrongEvidenceException {
        super(createPatternsBase());
        autoSupportFillEnable = true;
        versioningEnable = true;
    }

    private static DiagramPatternsBase createPatternsBase() {
        return new DiagramPatternsBase(createJPD());
    }

    private static JustificationPatternDiagram createJPD() {
        JustificationPatternDiagram jpd = new JustificationPatternDiagram();

        // TODO Missing: DE & feasibility
        InputType<RedmineDocumentEvidence> st0001 = new InputType<>("SWAM_ST_0001", new Type<>(RedmineDocumentEvidence.class,"SWAM_ST_0001"));
        InputType<RedmineDocumentApproval> st0001Approval = new InputType<>("SWAM_ST_0001_APPROVAL", new Type<>(RedmineDocumentApproval.class,"SWAM_ST_0001_APPROVAL"));
        OutputType<RedmineConclusion> st0001Validated = new OutputType<>("SWAM_ST_0001 validated", new Type<>(RedmineConclusion.class,"SWAM_ST_0001 validated"));
        Strategy swamSt0001Strategy = new CommonRedmineStrategy("Validate SWAM_ST_0001");
        Pattern pt=new Pattern("SWAM_ST_0001_VALIDATION", "Validation of SWAM_ST_0001", swamSt0001Strategy,
                Arrays.asList(st0001, st0001Approval), st0001Validated);
        pt.addApplicablePatternConstraint(new SameArtifactVersionApplicablePatternConstraint(pt));
        jpd.addStep(pt);

        List<InputType> conclusionInputs = new ArrayList<>();
        for (int i = 2; i < 14; i++) {
            Pattern stXPattern = intermediaryST(i, st0001Validated.transformToInput());
            conclusionInputs.add(stXPattern.getOutputType().transformToInput());
            jpd.addStep(stXPattern);
        }

        OutputType<RedmineConclusion> stValidated = new OutputType<>("SWAM_ST validated", new Type<>(RedmineConclusion.class,"SWAM_ST validated"));
        Strategy swamStStrategy = new TopLevelRedmineStrategy("Validate SWAM technical specification", "SWAM_ST validated");
        Pattern pattern=new Pattern("SWAM_ST_VALIDATION", "Validation of SWAM technical specification", swamStStrategy, conclusionInputs, stValidated);
        //pattern.addApplicablePatternConstraint(new SameArtifactVersionApplicablePatternConstraint(pattern));
        jpd.addStep(pattern);

        return jpd;
    }

    private static Pattern intermediaryST(int number, InputType<RedmineConclusion> swamSt0001IsValidated) {
        String fileName = "SWAM_ST_" + stNumber(number);

        InputType<RedmineDocumentEvidence> swamSt = new InputType<>(fileName, new Type<>(RedmineDocumentEvidence.class,fileName));
        InputType<RedmineDocumentApproval> swamStApproval = new InputType<>(fileName + "_APPROVAL",new Type<>(RedmineDocumentApproval.class,fileName+"_APPROVAL"));
        OutputType<RedmineConclusion> swamStValidated = new OutputType<>(fileName + " validated", new Type<>(RedmineConclusion.class,fileName
                +" validated"));
        Strategy swamStStrategy = new CommonRedmineStrategy("Validate " + fileName);
        Pattern pattern=new Pattern(fileName + "_VALIDATION", "Validation of " + fileName, swamStStrategy,
                Arrays.asList(swamSt0001IsValidated, swamSt, swamStApproval), swamStValidated);
        pattern.addApplicablePatternConstraint(new SameArtifactVersionApplicablePatternConstraint(pattern));
        return pattern;
    }

    private static String stNumber(int number) {
        StringBuilder strNumber = new StringBuilder(Integer.toString(number));

        while (strNumber.length() < 4) {
            strNumber.insert(0, "0");
        }

        return strNumber.toString();
    }
}
