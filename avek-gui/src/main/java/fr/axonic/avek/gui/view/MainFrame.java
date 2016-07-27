package fr.axonic.avek.gui.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Nathaël N on 26/07/16.
 */
public class MainFrame extends BorderPane {
	private final static Logger logger = Logger.getLogger(MainFrame.class);
	private final static URL FXML = MainFrame.class.getClassLoader()
			.getResource("fxml/views/MainFrame.fxml");

	@FXML
	private Button btnStrategy;


	private AbstractView view;

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

		// TODO Temporary solution
		setView(new GeneralizedView());
	}

	@FXML
	private void onClicStrategyButton(ActionEvent event) {
		AbstractView ancientview = view;
		StrategySelectionView ssv = new StrategySelectionView();
		setView(ssv);

		ssv.setAvailableChoices(
				ancientview instanceof TreatView?ancientview:new TreatView(),
				ancientview instanceof EstablishEffectView?ancientview:new EstablishEffectView(),
				ancientview instanceof GeneralizedView?ancientview:new GeneralizedView());
		ssv.setOnCancel(ancientview);
		ssv.onSetView(this::setView);

		btnStrategy.setDisable(true);
	}


	public void setView(AbstractView view) {
		setCenter(view); // remove abstract view currently loaded
		this.view = view;
		view.load();
		btnStrategy.setDisable(false);
	}
}
