package fr.axonic.avek.gui.components.parameters;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * Created by nathael on 22/08/16.
 */
public class MyNode extends HBox {
    public MyNode(Node ... nodes) {
        this.getChildren().addAll(nodes);
    }

    public double calculateWidth() {
        this.setMinWidth(this.computePrefWidth(this.getHeight()));
        return getMinWidth();
    }

    public void setPreferredWidth(double value) {
        setPrefWidth(value);
        setWidth(value);
    }
}
