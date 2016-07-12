package fr.axonic.avek.gui;

import fr.axonic.avek.gui.view.MainController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = MainController.getRoot();

		primaryStage.setTitle("GUI");
		Scene s = new Scene(root, 800, 600);
		primaryStage.setScene(s);

		primaryStage.show();
	}
}