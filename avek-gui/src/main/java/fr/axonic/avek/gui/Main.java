package fr.axonic.avek.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Parent root = null;
		try {
			List l = new ArrayList();
			String str = ".";
			Enumeration e = getClass().getClassLoader().getResources(str);
			while(e.hasMoreElements())
				l.add(e.nextElement());

			System.err.println("Resources under '"+ str + "': " + l);


			URL loc = getClass().getClassLoader().getResource("fxml/gui.fxml");
			if(loc == null)
				throw new NullPointerException();
			root = FXMLLoader.load(loc);
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		}

		primaryStage.setTitle("GUI");
		Scene s = new Scene(root, 800, 600);
		primaryStage.setScene(s);

		s.getStylesheets().add("css/expEffects/jellyBean.css");
		s.getStylesheets().add("css/expEffects/jellyBeanSelector.css");
		s.getStylesheets().add("css/expSubject/expSubject.css");
		primaryStage.show();
	}
}