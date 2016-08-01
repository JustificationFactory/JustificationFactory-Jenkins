package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.util.ViewOrchestrator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Nathaël N on 26/07/16.
 */
public class MainFrame extends BorderPane {
	private final static Logger logger = Logger.getLogger(MainFrame.class);
	private final static URL FXML = MainFrame.class.getClassLoader()
			.getResource("fxml/views/MainFrame.fxml");

	@FXML
	private Button btnStrategy;


	private ViewOrchestrator orchestrator;

	public MainFrame() {
		FXMLLoader fxmlLoader = new FXMLLoader(FXML);
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);

		logger.info("Loading MainFrame... (fxml)");
		try {
			fxmlLoader.load();
			logger.debug("MainFrame loaded.");
		} catch (IOException e) {
			logger.fatal("Impossible to load FXML for MainFrame", e);
		}
	}

	@FXML
	private void onClicStrategyButton(ActionEvent event) {
		List<ViewOrchestrator> orchs = orchestrator.getFollowing();
		if(orchs.size() == 1) {
			setView(orchs.get(0));
		} else {
			StrategySelectionView ssv = new StrategySelectionView();
			setCenter(ssv); // remove abstract view currently loaded
			ssv.load();

			ssv.setAvailableChoices(orchestrator.getFollowing());
			ssv.onSetView(this::setView);

			btnStrategy.setDisable(true);
		}
	}


	public void setView(ViewOrchestrator view) {
		if(view == null)
			return;

		this.orchestrator = view;
		AbstractView av = view.getView();
		if(av == null) {
			onClicStrategyButton(null);
		}
		else {
			setCenter(view.getView()); // remove abstract view currently loaded
			view.getView().load();
			btnStrategy.setDisable(false);
		}
	}
}
