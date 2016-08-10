package fr.axonic.avek.gui.components.parameters.leaves;

import fr.axonic.avek.gui.components.jellybeans.JellyBeanPane;
import fr.axonic.avek.gui.components.parameters.IExpParameter;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AVar;
import fr.axonic.base.engine.DiscretAVar;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Nathaël N on 21/07/16.
 */
public class RangedParameter extends SensitiveParameter {
    private final HBox generalizationPane;
    private final JellyBeanPane jellyBeanPane;

    public <T extends AVar & DiscretAVar> RangedParameter(int level, T paramValue) {
        super(level, paramValue);

        generalizationPane = new HBox();
        jellyBeanPane = new JellyBeanPane();
        generalizationPane.getChildren().add(jellyBeanPane);

        jellyBeanPane.setJellyBeansStateEditable(true);
        for (Object o : paramValue.getRange()) {
            Object value = ((AVar) o).getValue();
            if(value.equals(paramValue.getValue().toString())) {
                List<String> boolList = Arrays.asList("true", "false","unknown");
                jellyBeanPane.addJellyBean(value.toString(), boolList);
            }
            else {
                List<String> boolList = Arrays.asList("unknown", "true", "false");
                jellyBeanPane.addJellyBean(value.toString(), boolList);
            }
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


    @Override
    public AVar getAsAEntity() {
        AVar var = super.getAsAEntity();

        // Add range to var here

        return var;
    }
}
