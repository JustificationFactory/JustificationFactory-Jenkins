package fr.axonic.avek.gui.view.parameters;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

/**
 * Created by Nathaël N on 13/07/16.
 */
public class LevelMark extends HBox {
	private final String TRANSPARENT = "-fx-background-color: #fff0";
	private final String BLUE = "-fx-background-color: #2f25";
	private final String ARROW = "-fx-background-color: #2fa5";

	public LevelMark(int level) {
		this(level, false);
	}
	public LevelMark(int level, boolean withArrow) {
		for(int i=0; i<level; i++) {
			this.getChildren().add(new OneLevelMark(TRANSPARENT));
			this.getChildren().add(new OneLevelMark(BLUE));
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
			this.setStyle(c);
			this.setMinWidth(s);
			this.setPrefWidth(s);
			this.setMaxWidth(s);
		}
		OneLevelMark(String c) {
			this(c, 7);
		}
	}
}
