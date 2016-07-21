package fr.axonic.avek.gui.view.parameters.list.types;

import fr.axonic.avek.gui.view.parameters.list.SensitiveParameter;
import fr.axonic.avek.model.base.AString;
import javafx.event.ActionEvent;

import java.util.function.Consumer;

/**
 * Created by Nathaël N on 21/07/16.
 */
public class CategoryTitle extends SensitiveParameter {
	public CategoryTitle(int level, String label, Consumer<Boolean> onClickExpand) {
		super(level, new AString(label, ""));
		setExpandable(onClickExpand);
	}

	@Override
	protected void onClickMarkedUtil(ActionEvent event) {
		super.onClickMarkedUtil(event);
		setExpanded(isMarkedUtil());
	}
}
