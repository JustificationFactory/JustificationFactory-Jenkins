package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.components.MonitoredSystemPane;
import fr.axonic.avek.gui.components.parameters.GeneralizedParametersPane;
import fr.axonic.avek.gui.components.results.JellyBeansSelector;
import fr.axonic.avek.gui.model.json.Jsonifier;
import fr.axonic.avek.gui.model.structure.ExpEffect;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.engine.AEntity;
import fr.axonic.avek.model.base.engine.AList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
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

