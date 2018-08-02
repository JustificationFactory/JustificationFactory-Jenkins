package fr.axonic.avek.instance.redmine;

import fr.axonic.avek.engine.JustificationSystem;
import fr.axonic.avek.engine.diagram.JustificationPatternDiagram;
import fr.axonic.avek.engine.pattern.DiagramPatternsBase;

public class RedmineJustificationSystem extends JustificationSystem<DiagramPatternsBase> {

    public RedmineJustificationSystem() {
        this.autoSupportFillEnable=true;
        JustificationPatternDiagram jpd=new JustificationPatternDiagram();
        this.patternsBase=new DiagramPatternsBase(jpd);
    }

}
