package fr.axonic.avek.gui.view.parameters.list;

import fr.axonic.avek.model.base.engine.AVar;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by Nathaël N on 21/07/16.
 */
public abstract class SensitiveParameter extends ExpParameterLeaf {
	protected CheckBox markedUtil;
	private final MultiLevelMark levelMark;

	protected SensitiveParameter(int level, AVar paramValue) {
		super(paramValue);

		markedUtil = new CheckBox();
		markedUtil.setSelected(true);
		markedUtil.setOnAction(this::onClickMarkedUtil);

		levelMark = new MultiLevelMark(level);

		GridPane.setColumnIndex(markedUtil, 0);
		GridPane.setColumnIndex(levelMark, 1);
		GridPane.setColumnIndex(this.paramTitle, 2);
		GridPane.setColumnIndex(this.paramValue, 3);
	}

	protected void onClickMarkedUtil(ActionEvent event) {
		boolean b = markedUtil.isSelected();

		paramTitle.setDisable(!b);
		paramValue.setDisable(!b);
		levelMark.setDisable(!b);
	}

	protected boolean isMarkedUtil() {
		return markedUtil.isSelected();
	}

	@Override
	public Set<Node> getElements() {
		Set<Node> elements = super.getElements();
		elements.add(markedUtil);
		elements.add(levelMark);
		return elements;
	}

	protected void setExpandable(Consumer<Boolean> onClickExpand) {
		levelMark.setExpandable(onClickExpand);
	}

	protected void setExpanded(boolean expanded) {
		levelMark.setExpanded(expanded);
	}


	private class MultiLevelMark extends HBox {
		private final String TRANSPARENT = "levelmark_transparent";
		private final String LINE = "levelmark_line";
		private final String ARROW = "levelmark_arrow";

		private Consumer<Boolean> onClickExpand;
		private boolean expandable;
		private boolean expanded;
		private final int level;

		MultiLevelMark(int level) {
			this.level = level;
			this.expandable = false;
			this.expanded = true;

			recalculate();

			GridPane.setVgrow(this, Priority.ALWAYS);
		}

		private void setExpandable(Consumer<Boolean> onClickExpand) {
			this.onClickExpand = onClickExpand;
			expandable = true;
			recalculate();
		}

		private void expand(MouseEvent mouseEvent) {
			if (!this.isDisable())
				setExpanded(!expanded);
		}

		private void setExpanded(boolean expanded) {
			this.expanded = expanded;
			onClickExpand.accept(expanded);
		}

		private void recalculate() {
			this.getChildren().clear();

			for (int i = 0; i < level - 1; i++) {
				this.getChildren().add(new SingleLevelMark(TRANSPARENT));
				this.getChildren().add(new SingleLevelMark(LINE));
				this.getChildren().add(new SingleLevelMark(TRANSPARENT));
			}

			if (expandable) {
				this.getChildren().add(new SingleLevelMark(TRANSPARENT));

				SingleLevelMark arrow = new SingleLevelMark(ARROW);
				arrow.getChildren().add(new Label("◣")); // ⊗
				this.getChildren().add(arrow);
				this.getChildren().add(new SingleLevelMark(TRANSPARENT));

				arrow.setOnMouseClicked(this::expand);
			}
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
