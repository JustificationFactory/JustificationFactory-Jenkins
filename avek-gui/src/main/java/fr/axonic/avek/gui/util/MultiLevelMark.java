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
public class MultiLevelMark extends HBox {
	private final String TRANSPARENT = "levelmark_transparent";
	private final String LINE = "levelmark_line";
	private final String ARROW = "levelmark_arrow";

	private Consumer<Boolean> onClickExpand;
	private boolean expandable;
	private boolean expanded;
	private final int level;

	public MultiLevelMark(int level) {
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
		if (!this.isDisable())
			setExpanded(!expanded);
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
		onClickExpand.accept(expanded);
	}

	private void recalculate() {
		this.getChildren().clear();

		for (int i = level -1; i > 0; i--) {
			this.getChildren().add(new SingleLevelMark(TRANSPARENT));
			this.getChildren().add(new SingleLevelMark(LINE));
			this.getChildren().add(new SingleLevelMark(TRANSPARENT));
		}

		if (expandable) {
			this.getChildren().add(new SingleLevelMark(TRANSPARENT));

			SingleLevelMark arrow = new SingleLevelMark(ARROW);
			Label l = new Label("▼");
			arrow.getChildren().add(l);
			this.getChildren().add(arrow);
			this.getChildren().add(new SingleLevelMark(TRANSPARENT));

			arrow.setOnMouseClicked((event) -> {
				expand(event);

				RotateTransition rt = new RotateTransition(Duration.millis(500), l);
				rt.setByAngle((expanded?0:-90)-l.getRotate());
				rt.play();
			});
		}
	}

	private class SingleLevelMark extends Pane {
		SingleLevelMark(String c, int s) {
			this.getStyleClass().add(c);
			this.setMinWidth(s);
			this.setPrefWidth(s);
			this.setMaxWidth(s);
		}

		SingleLevelMark(String c) {
			this(c, 7);
		}
	}
}