package fr.axonic.avek.gui;

import fr.axonic.avek.gui.view.MainController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public class Main extends Application {
	private final static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		logger.debug("BEGIN");
		launch(args);
		logger.debug("END");
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = MainController.getRoot();

		primaryStage.setTitle("#AVEK analyzer");
		Scene s = new Scene(root, 800, 600);
		primaryStage.setScene(s);

		primaryStage.show();
		logger.debug("Frame created");
	}
}