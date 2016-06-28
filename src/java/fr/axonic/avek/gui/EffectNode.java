package fr.axonic.avek.gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

/**
 * Created by Nathaël N on 28/06/16.
 */
public class EffectNode extends HBox {

	private String name;

	public EffectNode(final Controller control, String choice) {
		this.name = choice;
		this.getStyleClass().add("effectNode");

		Label label = new Label(choice);
		this.getChildren().add(label);
		label.getStyleClass().add("effectNodeLabel");

		Button cross = new Button("x");
		this.getChildren().add(cross);
		cross.getStyleClass().add("effectNodeCross");

		cross.setOnMouseClicked(t -> control.removeEffectNode(EffectNode.this));
	}

	public String getName() {
		return name;
	}
}
