package fr.axonic.avek.gui.components.parameters.leaves;

import fr.axonic.avek.gui.components.parameters.ExpParameterLeaf;
import fr.axonic.avek.gui.components.parameters.MyNode;
import fr.axonic.avek.gui.components.parameters.ParameterLine;
import fr.axonic.base.engine.AVar;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;

import java.util.function.Consumer;

/**
 * Created by Nathaël N on 21/07/16.
 */
abstract class SensitiveParameter extends ExpParameterLeaf {
    CheckBox markedUtil;
    private Consumer<Boolean> onClickMarkedUtil;

    SensitiveParameter(int level, AVar paramValue) {
        super(level, paramValue);

        markedUtil = new CheckBox();
        markedUtil.setSelected(true);
        markedUtil.setOnAction(this::onClickMarkedUtil);
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
    public ParameterLine getParameterLine() {
        if(parameterLine == null) {
            ParameterLine p = super.getParameterLine();
            MyNode node = new MyNode(markedUtil);
            markedUtil.setMinWidth(20);
            p.addNode(node, "MARKED_UTIL", 0);
        }

        return super.getParameterLine();
    }
}
