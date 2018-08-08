package fr.axonic.avek.instance.redmine;

import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.diagram.JustificationPatternDiagram;
import fr.axonic.avek.engine.exception.WrongEvidenceException;
import fr.axonic.avek.engine.pattern.DiagramPatternsBase;
import fr.axonic.avek.engine.pattern.Pattern;
import fr.axonic.avek.engine.pattern.type.InputType;
import fr.axonic.avek.engine.pattern.type.OutputType;
import fr.axonic.avek.engine.strategy.Strategy;
import fr.axonic.validation.exception.VerificationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedmineJustificationSystem extends JustificationSystem<DiagramPatternsBase> {

    public RedmineJustificationSystem() throws VerificationException, WrongEvidenceException {
        super(createPatternsBase(), new ArrayList<>());
        autoSupportFillEnable = true;
        JustificationPatternDiagram jpd = new JustificationPatternDiagram();
        patternsBase = new DiagramPatternsBase(jpd);
    }

    private static DiagramPatternsBase createPatternsBase() {
        return new DiagramPatternsBase(createJPD());
    }

    private static JustificationPatternDiagram createJPD() {
        JustificationPatternDiagram jpd = new JustificationPatternDiagram();

        // TODO Missing: DE & feasibility
        InputType<RedmineDocumentEvidence> swamSt0001 = new InputType<>("SWAM_ST_0001", RedmineDocumentEvidence.class);
        InputType<RedmineDocumentApproval> swamSt0001Approval = new InputType<>("SWAM_ST_0001_APPROVAL", RedmineDocumentApproval.class);
        OutputType<RedmineConclusion> swamSt0001Validated = new OutputType<>("SWAM_ST_0001_VALIDATED", RedmineConclusion.class);
        Strategy swamSt0001Strategy = new RedmineStrategy();
        jpd.addStep(new Pattern("1", "SWAM_ST_0001_VALIDATION", swamSt0001Strategy,
                Arrays.asList(swamSt0001, swamSt0001Approval), swamSt0001Validated));

        InputType<RedmineConclusion> swamSt0001IsValidated = new InputType<>("SWAM_ST_0002_VALIDATED", RedmineConclusion.class);

        for (int i = 2; i < 14; i++) {
            jpd.addStep(intermediaryST(i, swamSt0001IsValidated));
        }

        List<InputType> conclusionInputs = new ArrayList<>();
        for (int i = 2; i < 14; i++) {
            conclusionInputs.add(new InputType<>("SWAM_ST_000" + i + "_VALIDATION", RedmineConclusion.class));
        }
        OutputType<RedmineConclusion> stValidated = new OutputType<>("SWAM_ST_VALIDATED", RedmineConclusion.class);
        Strategy swamStStrategy = new RedmineStrategy();
        jpd.addStep(new Pattern("14", "SWAM_ST_VALIDATION", swamStStrategy, conclusionInputs, stValidated));

        return jpd;
    }

    private static Pattern intermediaryST(int number, InputType<RedmineConclusion> swamSt0001IsValidated) {
        InputType<RedmineDocumentEvidence> swamSt = new InputType<>("SWAM_ST_000" + number, RedmineDocumentEvidence.class);
        InputType<RedmineDocumentApproval> swamStApproval = new InputType<>("SWAM_ST_000" + number + "_APPROVAL", RedmineDocumentApproval.class);
        OutputType<RedmineConclusion> swamStValidated = new OutputType<>("SWAM_ST_000" + number + "_VALIDATED", RedmineConclusion.class);
        Strategy swamStStrategy = new RedmineStrategy();

        return new Pattern(Integer.toString(number), "SWAM_ST_000" + number + "_VALIDATION", swamStStrategy,
                Arrays.asList(swamSt0001IsValidated, swamSt, swamStApproval), swamStValidated);
    }
}
