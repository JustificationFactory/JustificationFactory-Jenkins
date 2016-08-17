package fr.axonic.avek.gui.util;

import javafx.animation.RotateTransition;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.util.Duration;

import java.util.function.Consumer;

/**
 * Created by Nathaël N on 26/07/16.
 */
public class LevelMark extends HBox {
    private static final String LINE = "levelmark-line";
    private static final String ARROW = "levelmark-arrow";
    private static final int ROTATE_DURATION = 500; // ms

    private Consumer<Boolean> onClickExpand;
    private boolean expandable;
    private boolean expanded;
    private final int level;
    private Label icon;

    public LevelMark(int level) {
        this.level = level;
        this.expandable = false;
        this.expanded = true;

        recalculate();

        GridPane.setVgrow(this, Priority.ALWAYS);
    }

    /**
     * Set an observer that will be called each time LevelMark expands or retracts itself
     * @param onClickExpand Method to call after user clicked on Expand or Retract
     */
    public void setOnExpand(Consumer<Boolean> onClickExpand) {
        this.onClickExpand = onClickExpand;
        expandable = true;
        recalculate();
    }

    private void onClick(MouseEvent mouseEvent) {
        if (!this.isDisable()) {
            setExpanded(!expanded);
        }
    }

    /**
     * Set Level mark as expanded (with a 'true') or as retracted (with a 'false')
     * @param expanded answer the question "Will the LevelMark be expanded ?"
     */
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        onClickExpand.accept(expanded);

        // Rotate the arrow mark
        RotateTransition rt = new RotateTransition(Duration.millis(ROTATE_DURATION), icon);
        rt.setByAngle((expanded ? 0 : -90) - icon.getRotate());
        rt.play();
    }

    private void recalculate() {
        this.getChildren().clear();

        // Add a graphic line for each indent level
        for (int i = level; i > 0; i--) {
            Pane p = new Pane();
            p.getStyleClass().add(LINE);
            this.getChildren().add(p);
        }

        // If is expandable, so add an arrow
        if (expandable) {
            Pane p = new Pane();
            p.getStyleClass().add(ARROW);
            this.getChildren().add(p);


            icon = new Label("▼");
            p.getChildren().add(icon);

            this.setOnMouseClicked(this::onClick);
        }
    }
}