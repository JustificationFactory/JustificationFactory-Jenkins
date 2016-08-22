package fr.axonic.avek.gui.components.parameters;

import fr.axonic.avek.gui.util.LevelMark;
import fr.axonic.base.AContinuousNumber;
import fr.axonic.base.engine.AVar;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.function.Consumer;

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
            } else if(paramValue instanceof AContinuousNumber) {
                this.paramValue.setText(paramValue.getValue().toString()
                        + " "+ ((AContinuousNumber)paramValue).getUnit());
            } else {
                this.paramValue.setText(paramValue.getValue().toString());
            }
        }
    }

    protected ParameterLine parameterLine;
    @Override
    public ParameterLine getParameterLine() {
        if(parameterLine == null) {
            parameterLine = new ParameterLine();
            parameterLine.addNode(new MyNode(levelMark, paramTitle), "TITLE", 1);

            if (!paramValue.getText().isEmpty()) {
                parameterLine.addNode(new MyNode(new Label("  :  ")), ":", 2);
                parameterLine.addNode(new MyNode(paramValue), "VALUE", 3);
            }
        }

        return parameterLine;
    }

    public void setExpandable(Consumer<Boolean> onClickExpand) {
        levelMark.setOnExpand(onClickExpand);
    }

    public void setExpanded(boolean expanded) {
        levelMark.setExpanded(expanded);
    }

    public Label getLabelTitle() {
        return paramTitle;
    }
}
