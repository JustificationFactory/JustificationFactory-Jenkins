package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.Controller;
import fr.axonic.avek.gui.model.IEffect;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Created by Nathaël N on 28/06/16.
 */
public class EffectNode extends HBox {
	private IEffect value;

	public EffectNode(final Controller control, IEffect value) {
		this.value = value;
		this.getStyleClass().add("effectNode");

		Label label = new Label(value.getName());
		this.getChildren().add(label);
		label.getStyleClass().add("effectNodeLabel");

		Button cross = new Button("x");
		this.getChildren().add(cross);
		cross.getStyleClass().add("effectNodeCross");

		cross.setOnMouseClicked(t -> control.removeEffectNode(EffectNode.this));
	}

	public IEffect getIEffect() {
		return value;
	}
}
