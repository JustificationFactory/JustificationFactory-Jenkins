package fr.axonic.avek.gui.components.parameters;

import fr.axonic.avek.gui.util.LevelMark;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AVar;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by Nathaël N on 04/07/16.
 */
public class ExpParameterLeaf implements IExpParameter {
    // GUI Component
    protected final Label paramTitle;
    protected final Label paramValue;
    private final AVar var;
    protected final LevelMark levelMark;

    public ExpParameterLeaf(int level, AVar paramValue) {
        // Init elements
        this.var = paramValue;
        this.paramTitle = new Label(paramValue.getLabel());
        this.paramValue = new Label();

        levelMark = new LevelMark(level);

        if(paramValue.getValue() != null && !paramValue.getValue().toString().equals("")) {
            // Pretty print dates
            if(paramValue.getValue() instanceof Calendar) {
                SimpleDateFormat df = new SimpleDateFormat();
                df.applyPattern("dd/MM/yyyy HH:mm:ss");
                this.paramValue.setText(df.format(((Calendar)paramValue.getValue()).getTime()));
            } else {
                this.paramValue.setText(" : " + paramValue.getValue().toString());
            }
        }

        GridPane.setColumnIndex(this.levelMark, 0);
        GridPane.setColumnIndex(this.paramTitle, 1);
        GridPane.setColumnIndex(this.paramValue, 2);
    }

    @Override
    public Set<Node> getElements() {
        Set<Node> elts = new HashSet<>();

        elts.add(levelMark);
        elts.add(paramTitle);
        elts.add(paramValue);

        return elts;
    }

    @Override
    public String getName() {
        return paramTitle.getText();
    }

    protected void onClickMarkedUtil(ActionEvent event) {
        paramTitle.setDisable(true);
        paramValue.setDisable(true);
    }

    public void setExpandable(Consumer<Boolean> onClickExpand) {
        levelMark.setExpandable(onClickExpand);
    }

    protected void setExpanded(boolean expanded) {
        levelMark.setExpanded(expanded);
    }

    public Label getLabelTitle() {
        return paramTitle;
    }

    @Override
    public AVar getAsAEntity() {
        return var;
    }
}
