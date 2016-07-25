package fr.axonic.avek.gui;

import fr.axonic.avek.gui.util.ConcurrentTaskManager;
import fr.axonic.avek.gui.view.MainPanel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

public class Main extends Application {
	private final static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		ConcurrentTaskManager ctm = new ConcurrentTaskManager();

		logger.debug("Loading Frame...");

		primaryStage.setTitle("#AVEK analyzer");
		MainPanel mainPanel = new MainPanel();

		Scene s = new Scene(mainPanel, 800, 600);
		primaryStage.setScene(s);

		primaryStage.show();
		logger.debug("Frame created.");

		ctm.runLaterOnThread(() -> {
			try {
				Thread.sleep(2000);
				ctm.runLaterOnPlatform(() -> mainPanel.setMonitoredSystem(Main.getFileContent("files/subjectFile.json")));
				Thread.sleep(2000);
				ctm.runLaterOnPlatform(() -> mainPanel.setExperimentParameters(Main.getFileContent("files/parametersFile.json")));
				Thread.sleep(6000);
				ctm.runLaterOnPlatform(() -> mainPanel.setEffectSelector());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public static void disableGraphics() {
		System.setProperty("testfx.robot", "glass");
		System.setProperty("prism.order", "sw");
		System.setProperty("testfx.headless", "true");
		System.setProperty("java.awt.headless", "true");
		System.setProperty("prism.text", "t2k");
	}

	public static String getFileContent(String path) {
		String res = "";
		try {
			File f = new File(Main.class.getClassLoader()
					.getResource(path).toURI());
			List<String> ls = Files.readAllLines(f.toPath());
			for (String s : ls)
				res += s;

		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return res;
	}
}