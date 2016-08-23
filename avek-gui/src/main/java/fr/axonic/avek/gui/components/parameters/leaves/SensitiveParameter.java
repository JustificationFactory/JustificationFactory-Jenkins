package fr.axonic.avek.gui.components.parameters.leaves;

import fr.axonic.base.engine.AVar;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Nathaël N on 21/07/16.
 */
abstract class SensitiveParameter extends ExpParameterLeaf {
    CheckBox markedUtil;
    private Consumer<Boolean> onClickMarkedUtil;

    SensitiveParameter(int level, AVar var) {
        super(level, var);

        markedUtil = new CheckBox();
        markedUtil.setSelected(true);
        markedUtil.setOnAction(this::onClickMarkedUtil);

        // set style
        GridPane.setColumnIndex(markedUtil, 0);
        GridPane.setColumnIndex(paneTitle, 1); // was 0
        GridPane.setColumnIndex(separator, 2); // was 1
        GridPane.setColumnIndex(paramValue, 3); // was 2
    }

    protected void onClickMarkedUtil(ActionEvent event) {
        boolean b = markedUtil.isSelected();

        paramTitle.setDisable(!b);
        paramValue.setDisable(!b);
        levelMark.setDisable(!b);
        if (onClickMarkedUtil != null) {
            onClickMarkedUtil.accept(b);
        }
    }

    public void setOnClickMarkedUtil(Consumer<Boolean> onClickMarkedUtil) {
        this.onClickMarkedUtil = onClickMarkedUtil;
    }

    @Override
    public List<Node> getNodeLine() {
        List<Node> list = super.getNodeLine();
        list.add(markedUtil);
        return list;
    }
}
