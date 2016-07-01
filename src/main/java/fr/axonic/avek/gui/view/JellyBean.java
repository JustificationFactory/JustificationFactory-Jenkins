package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.Controller;
import fr.axonic.avek.gui.model.IResultElement;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Created by Nathaël N on 28/06/16.
 */
public class JellyBean extends HBox {
	private IResultElement value;

	public JellyBean(final Controller control, IResultElement value) {
		this.value = value;
		this.getStyleClass().add("jellyBean");

		Label label = new Label(value.getName());
		this.getChildren().add(label);
		label.getStyleClass().add("jellyBeanLabel");

		Button cross = new Button("x");
		this.getChildren().add(cross);
		cross.getStyleClass().add("jellyBeanCross");

		cross.setOnMouseClicked(t -> control.removeEffectNode(JellyBean.this));
	}

	public IResultElement getIEffect() {
		return value;
	}
}
