package fr.axonic.avek.gui.components.parameters.leaves;

import fr.axonic.avek.gui.components.parameters.IExpParameter;
import fr.axonic.avek.gui.util.LevelMark;
import fr.axonic.base.AContinuousNumber;
import fr.axonic.base.engine.AVar;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Nathaël N on 04/07/16.
 */
public class ExpParameterLeaf implements IExpParameter {
    // GUI Component
    final HBox paneTitle;
    final Label paramTitle;
    final Label paramValue;
    final LevelMark levelMark;

    // " : " label
    final Label separator;

    public ExpParameterLeaf(int level, AVar var) {
        // Init elements
        levelMark = new LevelMark(level);
        paramTitle = new Label(var.getLabel());
        paramValue = new Label();
        separator = new Label(":");
        paneTitle = new HBox();
        paneTitle.getChildren().setAll(levelMark, paramTitle);

        if(var.getValue() != null && !var.getValue().toString().equals("")) {
            // Pretty print dates
            if(var.getValue() instanceof Calendar) {
                SimpleDateFormat df = new SimpleDateFormat();
                df.applyPattern("dd/MM/yyyy HH:mm:ss");
                this.paramValue.setText(df.format(((Calendar)var.getValue()).getTime()));
            } else if(var instanceof AContinuousNumber) {
                this.paramValue.setText(var.getValue().toString()
                        + " "+ ((AContinuousNumber)var).getUnit());
            } else {
                this.paramValue.setText(var.getValue().toString());
            }
        }

        // set css
        paneTitle.getStyleClass().add( "param-panetitle");
        separator.getStyleClass().add( "param-separator");
        paramValue.getStyleClass().add("param-value");

        GridPane.setColumnIndex(paneTitle, 0);
        GridPane.setColumnIndex(separator, 1);
        GridPane.setColumnIndex(paramValue, 2);
    }


    List<Node> getNodeLine() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(paneTitle);

        if(!paramValue.getText().isEmpty()) {
            nodes.add(separator);
            nodes.add(paramValue);
        }

        return nodes;
    }
    @Override
    public final List<List<Node>> getNodes() {
        List<List<Node>> list = new ArrayList<>();
        list.add(getNodeLine());
        return list;
    }


    public void setExpandable(Consumer<Boolean> onClickExpand) {
        levelMark.setOnExpand(onClickExpand);
    }
}
