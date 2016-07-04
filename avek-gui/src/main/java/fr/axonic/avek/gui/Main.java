package fr.axonic.avek.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/gui.fxml"));
		primaryStage.setTitle("GUI");
		primaryStage.setScene(new Scene(root, 800, 600));

		primaryStage.getScene().getStylesheets().add("css/expEffects/jellyBean.css");
		primaryStage.getScene().getStylesheets().add("css/expEffects/jellyBeanSelector.css");
		primaryStage.getScene().getStylesheets().add("css/expSubject/expSubject.css");
		primaryStage.show();
	}
}
