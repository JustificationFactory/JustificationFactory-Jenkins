package fr.axonic.avek.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Parent root = null;
		try {
			String path = ".";

			List<File> lf = new ArrayList<>();
			List<File> lfTotal = new ArrayList<>();
			lf.add(new File(path));

			while(!lf.isEmpty())
			for(File f : new ArrayList<>(lf)) {
				lf.remove(f);
				if(f.isDirectory())
					Collections.addAll(lf, f.listFiles());
				else
					lfTotal.add(f);
			}
			System.err.println(lfTotal);

			URL loc = getClass().getClassLoader().getResource("fxml/gui.fxml");
			if(loc == null)
				throw new NullPointerException();
			root = FXMLLoader.load(loc);
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		}

		primaryStage.setTitle("#AVEK analyzer");
		Scene s = new Scene(root, 800, 600);
		primaryStage.setScene(s);

		primaryStage.show();
	}
}