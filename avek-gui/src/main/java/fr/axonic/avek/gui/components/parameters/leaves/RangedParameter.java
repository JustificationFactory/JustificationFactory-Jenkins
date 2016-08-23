package fr.axonic.avek.gui.components.parameters.leaves;

import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanPane;
import fr.axonic.base.engine.AEnumItem;
import fr.axonic.base.engine.AVar;
import fr.axonic.base.engine.DiscretAVar;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Nathaël N on 21/07/16.
 */
public class RangedParameter extends SensitiveParameter {
    private final HBox generalizationPane;
    private final JellyBeanPane jellyBeanPane;

    public <T extends AEnumItem, U extends AVar<T> & DiscretAVar<U>>
    RangedParameter(int level, final U stateList) {
        super(level, stateList);

        generalizationPane = new HBox();
        jellyBeanPane = new JellyBeanPane();
        generalizationPane.getChildren().add(jellyBeanPane);

        jellyBeanPane.setAllJellyBeansEditable(true);

        // For each states of stateList
        // Copying stateList before, because will be edited
        for (final U state : new HashSet<>(stateList.getRange())) {
            T value = state.getValue();
            String strVal = value.toString();

            // Each states can be set as True or False for generalization
            JellyBeanItem<Boolean> item = new JellyBeanItem<>(value, Arrays.asList(false, true));
            jellyBeanPane.addJellyBean(item);

            if(strVal.equals(stateList.getValue().toString())) {
                // this state cannot be checked 'false' at generalization
                // because it is the value used for the experimentation
                item.setState(true);
                item.setEditable(false);
            } else {
                // this state is removed from the stateList
                // because isn't checked 'true' at generalization
                stateList.getRange().remove(state);

                // If user click on the jelly bean of this state
                // this state will be added to stateList
                // otherwise it will be removed from
                item.addStateChangeListener((lastState, newState) -> {
                    if (!newState) {
                        stateList.getRange().remove(state);
                    }else if (!stateList.getRange().contains(state)) {
                        stateList.getRange().add(state);
                    }
                });
            }
        }

        // GridPane.setColumnIndex(markedUtil, 0); // Done in superclass
        // GridPane.setColumnIndex(levelMark, 1);
        // GridPane.setColumnIndex(paramTitle, 2);
        // GridPane.setColumnIndex(separator, 3);
        // GridPane.setColumnIndex(paramValue, 4);
        GridPane.setColumnIndex(generalizationPane, 5);
    }

    @Override
    protected void onClickMarkedUtil(ActionEvent event) {
        super.onClickMarkedUtil(event);

        boolean b = markedUtil.isSelected();
        // if unchecked, so disable component edition
        jellyBeanPane.setDisable(!b);
    }


    @Override
    public List<Node> getNodeLine() {
        List<Node> list = super.getNodeLine();
        list.add(generalizationPane);
        return list;
    }
}
