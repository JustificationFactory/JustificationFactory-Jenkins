package fr.axonic.avek.gui;

import fr.axonic.avek.gui.util.ConcurrentTaskManager;
import fr.axonic.avek.gui.util.Util;
import fr.axonic.avek.gui.view.GeneralizedView;
import fr.axonic.avek.gui.view.MainFrame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public class Main extends Application {
	private final static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		ConcurrentTaskManager ctm = new ConcurrentTaskManager();

		logger.debug("Loading MainFrame...");

		primaryStage.setTitle("#AVEK analyzer");

		MainFrame mainFrame = new MainFrame();
		Scene s = new Scene(mainFrame, 800, 600);
		primaryStage.setScene(s);

		primaryStage.show();
		logger.debug("MainFrame created.");


		ctm.runLaterOnThread(() -> {
			try {
				GeneralizedView generalizedView = new GeneralizedView();
				Thread.sleep(2000);
				ctm.runLaterOnPlatform(() -> mainFrame.setView(generalizedView));
				Thread.sleep(2000);
				ctm.runLaterOnPlatform(() -> generalizedView.setMonitoredSystem(Util.getFileContent("files/subjectFile.json")));
				Thread.sleep(2000);
				ctm.runLaterOnPlatform(() -> generalizedView.setExperimentParameters(Util.getFileContent("files/parametersFile.json")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}