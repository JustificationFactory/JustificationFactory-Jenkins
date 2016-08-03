package fr.axonic.avek.gui.components.parameters.list.leaves;

import fr.axonic.avek.gui.components.jellyBeans.JellyBeanPane;
import fr.axonic.base.engine.AVar;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by Nathaël N on 21/07/16.
 */
public class RangedParameter extends SensitiveParameter {
	private final HBox generalizationPane;
	private final JellyBeanPane jellyBeanPane;

	public RangedParameter(int level, AVar paramValue, List<AVar> values) {
		super(level, paramValue);

		generalizationPane = new HBox();
		jellyBeanPane = new JellyBeanPane();
		generalizationPane.getChildren().add(jellyBeanPane);

		jellyBeanPane.setJellyBeansStateEditable(true);
		List<String> boolList = Arrays.asList("unknown", "true", "false");
		for(AVar value : values)
			jellyBeanPane.addJellyBean(value.getValue().toString(), boolList);

		// GridPane.setColumnIndex(markedUtil, 0); // Already done by superclass
		// GridPane.setColumnIndex(levelMark, 1);
		// GridPane.setColumnIndex(this.paramTitle, 2);
		// GridPane.setColumnIndex(this.paramValue, 3);
		GridPane.setColumnIndex(generalizationPane, 4);
	}

	@Override
	public Set<Node> getElements() {
		Set<Node> elts = super.getElements();
		elts.add(generalizationPane);

		return elts;
	}
}
