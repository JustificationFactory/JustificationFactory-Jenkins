package fr.axonic.avek.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        primaryStage.setTitle("Need a name");
        primaryStage.setScene(new Scene(root, 400, 275));

	    primaryStage.getScene().getStylesheets().add("css/style.css");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
