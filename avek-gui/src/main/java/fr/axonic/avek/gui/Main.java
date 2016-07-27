package fr.axonic.avek.gui;

import fr.axonic.avek.gui.view.MainFrame;
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
		Scene s = new Scene(mainFrame, 800, 600);
		primaryStage.setScene(s);

		primaryStage.show();
		logger.debug("MainFrame created.");
	}

	MainFrame getMainFrame() {
		return mainFrame;
	}
}