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
    private final String LINE = "levelmark-line";
    private final String ARROW = "levelmark-arrow";
    private final int ROTATE_DURATION = 500; // ms

    private Consumer<Boolean> onClickExpand;
    private boolean expandable;
    private boolean expanded;
    private final int level;

    public LevelMark(int level) {
        this.level = level;
        this.expandable = false;
        this.expanded = true;

        recalculate();

        GridPane.setVgrow(this, Priority.ALWAYS);
    }

    public void setExpandable(Consumer<Boolean> onClickExpand) {
        this.onClickExpand = onClickExpand;
        expandable = true;
        recalculate();
    }

    private void expand(MouseEvent mouseEvent) {
        if (!this.isDisable()) {
            setExpanded(!expanded);
        }
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
        onClickExpand.accept(expanded);
    }

    private void recalculate() {
        this.getChildren().clear();

        for (int i = level; i > 0; i--) {
            Pane p = new Pane();
            p.getStyleClass().add(LINE);
            this.getChildren().add(p);
        }

        if (expandable) {
            Pane p = new Pane();
            p.getStyleClass().add(ARROW);
            this.getChildren().add(p);


            Label l = new Label("▼");
            p.getChildren().add(l);

            this.setOnMouseClicked((event) -> {
                expand(event);

                RotateTransition rt = new RotateTransition(Duration.millis(ROTATE_DURATION), l);
                rt.setByAngle((expanded ? 0 : -90) - l.getRotate());
                rt.play();
            });
        }
    }
}