package fr.axonic.avek.gui.components.parameters;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nathael on 22/08/16.
 */
public class ParameterLine extends GridPane {
    private final static Logger LOGGER = Logger.getLogger(ParameterLine.class);
    private static final Map<String, Double> SIZES = new HashMap<>();
    private static final Map<MyNode, String> ALL_NODES = new HashMap<>();

    public ParameterLine() {
        getStyleClass().add("parameter-line");
        setStyle("-fx-padding: 1px 0");
    }

    public void addNode(MyNode n, String type, int columnIndex) {
        GridPane.setColumnIndex(n, columnIndex);
        super.getChildren().add(n);

        ALL_NODES.put(n, type);

        n.setAlignment(Pos.CENTER_LEFT);

        n.applyCss(); // force-it to calculate his size
        n.layout(); // force-calcultate too

        double nodeSize = n.calculateWidth();
        Platform.runLater(ParameterLine::computeSizes);
    }

    public synchronized static void computeSizes() {
        // Computing sizes
        SIZES.clear();

        for (Map.Entry<MyNode, String> entry : ALL_NODES.entrySet()) {
            String type = entry.getValue();
            MyNode node = entry.getKey();

            double nodeSize = node.calculateWidth();
            if (!SIZES.containsKey(type) || SIZES.get(type) < nodeSize) {
                SIZES.put(type, nodeSize);
            }
        }

        /* // DEBUG Colors ↓
        Map<String, Color> colorMap = new HashMap<>();
        for(String s : SIZES.keySet()) {
            colorMap.put(s, Color.color(Math.random(), Math.random(), Math.random(), 0.3));
        }
        */ // DEBUG Colors ↑

        // Updating nodes
        for (Map.Entry<MyNode, String> entry : ALL_NODES.entrySet()) {
            entry.getKey().setPreferredWidth(SIZES.get(entry.getValue()));

            /* // DEBUG Colors ↓
            entry.getKey().setBackground(
                    new Background(
                            new BackgroundFill(
                                    colorMap.get(entry.getValue()),
                                    CornerRadii.EMPTY,
                                    Insets.EMPTY)));
            */ // DEBUG Colors
        }
    }

    @Deprecated
    @Override
    public ObservableList<Node> getChildren() {
        return super.getChildren();
    }
}