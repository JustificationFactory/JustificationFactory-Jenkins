package fr.axonic.avek.gui.components.jellyBeans;

import fr.axonic.avek.gui.model.structure.ExperimentResult;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

import java.util.function.Consumer;

/**
 * Created by Nathaël N on 28/07/16.
 */
public class JellyBeanPane extends FlowPane {

	private Consumer<String> onRemoveJellyBean;

	public void addJellyBean(ExperimentResult choice) {
		JellyBean jb2 = new JellyBean();
		jb2.setStateType(choice.getStateClass());
		jb2.setText(choice.getName());

		if(onRemoveJellyBean != null)
			jb2.setOnDelete(this::removeJellyBean);

		getChildren().add(jb2);
	}

	private void removeJellyBean(JellyBean jbc) {
		getChildren().remove(jbc);
		if(onRemoveJellyBean != null)
			onRemoveJellyBean.accept(jbc.getText());
	}
	void onRemoveJellyBean(Consumer<String> function) {
		this.onRemoveJellyBean = function;

		for(Node n : getChildren()) {
			JellyBean jb = (JellyBean)n;
			jb.setOnDelete(function==null?null:this::removeJellyBean);
		}
	}
}
