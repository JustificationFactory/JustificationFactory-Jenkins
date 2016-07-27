package fr.axonic.avek.gui.view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StrategySelectionView extends AbstractView {
	private final static Logger logger = Logger.getLogger(StrategySelectionView.class);
	private final static String FXML = "fxml/views/StrategySelectionView.fxml";

	@FXML
	private Button submit;
	@FXML
	private Button cancel;
	@FXML
	private ComboBox<PointerOnView> comboBox;

	private AbstractView onCancelView;
	private Consumer<AbstractView> onSetViewMethod;

	@Override
	protected void onLoad() {
		logger.info("Loading StrategySelectionView...");
		super.load(FXML);
		logger.debug("StrategySelectionView loaded.");
	}

	@FXML
	void onSubmit(ActionEvent event) {
		onSetView(comboBox.getValue().view);
	}
	@FXML
	void onCancel(ActionEvent event) {
		onSetView(onCancelView);
	}

	void setAvailableChoices(AbstractView... views) {
		List<PointerOnView> lpov = new ArrayList<>();
		for(AbstractView av : views)
			lpov.add(new PointerOnView(av));

		comboBox.setItems(FXCollections.observableArrayList(lpov));
	}
	void setOnCancel(AbstractView view) {
		onCancelView = view;
	}

	private void onSetView(AbstractView view) {
		onSetViewMethod.accept(view);
	}
	void onSetView(Consumer<AbstractView> onSetViewMethod) {
		this.onSetViewMethod = onSetViewMethod;
	}

	private class PointerOnView {
		final AbstractView view;

		private PointerOnView(AbstractView view) {
			this.view = view;
		}

		@Override
		public String toString() {
			return view.getClass().getSimpleName();
		}
	}
}

