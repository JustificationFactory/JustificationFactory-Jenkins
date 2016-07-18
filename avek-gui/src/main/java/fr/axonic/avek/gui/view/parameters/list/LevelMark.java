package fr.axonic.avek.gui.view.parameters.list;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * Created by Nathaël N on 13/07/16.
 */
public class LevelMark extends HBox {
	private final String TRANSPARENT = "levelmark_transparent";
	private final String LINE = "levelmark_line";
	private final String ARROW = "levelmark_arrow";

	LevelMark(int level) {
		this(level, false);
	}
	LevelMark(int level, boolean withArrow) {
		for(int i=0; i<level; i++) {
			this.getChildren().add(new OneLevelMark(TRANSPARENT));
			this.getChildren().add(new OneLevelMark(LINE));
			this.getChildren().add(new OneLevelMark(TRANSPARENT));
		}
		if(withArrow) {
			this.getChildren().add(new OneLevelMark(TRANSPARENT));
			this.getChildren().add(new OneLevelMark(ARROW));
			this.getChildren().add(new OneLevelMark(TRANSPARENT));
		}

		GridPane.setVgrow(this, Priority.ALWAYS);
	}

	private class OneLevelMark extends Pane {
		OneLevelMark(String c, int s) {
			this.getStyleClass().add(c);
			this.setMinWidth(s);
			this.setPrefWidth(s);
			this.setMaxWidth(s);
		}
		OneLevelMark(String c) {
			this(c, 7);
		}
	}
}
