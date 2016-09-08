package fr.axonic.avek.gui.components.parameters.leaves;

import fr.axonic.base.engine.AVar;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

import java.util.List;

/**
 * Created by Nathaël N on 21/07/16.
 */
abstract class SensitiveParameter extends ExpParameterLeaf {
    final CheckBox markedUtil;
    private final AVar aVar;

    SensitiveParameter(int level, AVar var) {
        super(level, var);

        this.aVar = var;

        markedUtil = new CheckBox();
        markedUtil.setOnAction(event -> setSelected(markedUtil.isSelected()));

        //TODO not working correctly: checkbox unchecked ok, but text not disabled
        // Platform.runLater(() -> markedUtil.setSelected(var.isMandatory()));
        markedUtil.setSelected(true);

        // set style
        GridPane.setColumnIndex(markedUtil, 0);
        GridPane.setColumnIndex(paneTitle, 1); // was 0
        GridPane.setColumnIndex(separator, 2); // was 1
        GridPane.setColumnIndex(paramValue, 3); // was 2
    }

    @Override
    List<Node> getNodeLine() {
        List<Node> list = super.getNodeLine();
        list.add(markedUtil);
        return list;
    }

    protected void setSelected(boolean b) {
        aVar.setMandatory(b);
        paramTitle.setDisable(!b);
        paramValue.setDisable(!b);
        levelMark.setDisable(!b);
        levelMark.setExpanded(b);
    }
}
