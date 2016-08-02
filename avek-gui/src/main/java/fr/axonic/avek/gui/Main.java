package fr.axonic.avek.gui;

import fr.axonic.avek.gui.util.ViewOrchestrator;
import fr.axonic.avek.gui.view.EstablishEffectView;
import fr.axonic.avek.gui.view.GeneralizedView;
import fr.axonic.avek.gui.view.MainFrame;
import fr.axonic.avek.gui.view.TreatView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public class Main extends Application {
	private final static Logger logger = Logger.getLogger(Main.class);

	private MainFrame mainFrame;

	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) throws IOException {
		logger.debug("Loading MainFrame...");

		primaryStage.setTitle("#AVEK analyzer");

		mainFrame = new MainFrame();
		Scene s = new Scene(mainFrame);
		primaryStage.setScene(s);

		primaryStage.show();
		logger.debug("MainFrame created.");

		ViewOrchestrator oNull = new ViewOrchestrator(null);
		ViewOrchestrator o3 = new ViewOrchestrator(new GeneralizedView());
		o3.addFollowing(oNull);
		ViewOrchestrator o2 = new ViewOrchestrator(new EstablishEffectView());
		o2.addFollowing(o3);
		ViewOrchestrator o1 = new ViewOrchestrator(new TreatView());
		o1.addFollowing(o2);

		oNull.addFollowing(o1);
		oNull.addFollowing(o2);
		oNull.addFollowing(o3);

		mainFrame.setView(oNull);
	}

	MainFrame getMainFrame() {
		return mainFrame;
	}
}