package fr.axonic.avek.gui.components.parameters.leaves;

import fr.axonic.avek.gui.components.jellybeans.JellyBeanPane;
import fr.axonic.base.engine.AVar;
import fr.axonic.base.engine.DiscretAVar;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by Nathaël N on 21/07/16.
 */
public class RangedParameter extends SensitiveParameter {
    private final HBox generalizationPane;
    private final JellyBeanPane jellyBeanPane;

    public <T, U extends AVar<T> & DiscretAVar<U>>
    RangedParameter(int level, U paramValue) {
        super(level, paramValue);

        generalizationPane = new HBox();
        jellyBeanPane = new JellyBeanPane();
        generalizationPane.getChildren().add(jellyBeanPane);

        jellyBeanPane.setJellyBeansStateEditable(true);
        for (U state : paramValue.getRange()) {
            T value = state.getValue();
            String strVal = value.toString();

            if(strVal.equals(paramValue.getValue().toString())) {
                List<String> boolList = Arrays.asList("true", "false");
                jellyBeanPane.addJellyBean(strVal, boolList);
            } else {
                List<String> boolList = Arrays.asList("false", "true");
                jellyBeanPane.addJellyBean(strVal, boolList);
            }
            jellyBeanPane.addJellyBeanListener(strVal, (newState) -> {
                if (Boolean.getBoolean(newState)) {
                    if (paramValue.getRange().contains(state)) {
                        paramValue.getRange().add(state);
                    }
                }else {
                    paramValue.getRange().remove(state);
                }
            });
        }

        // GridPane.setColumnIndex(markedUtil, 0); // Already done by superclass
        // GridPane.setColumnIndex(levelMark, 1);
        // GridPane.setColumnIndex(this.paramTitle, 2);
        // GridPane.setColumnIndex(this.paramValue, 3);
        GridPane.setColumnIndex(generalizationPane, 4);
    }

    @Override
    public Set<Node> getElements() {
        Set<Node> elts = super.getElements();
        elts.add(generalizationPane);

        return elts;
    }
}
