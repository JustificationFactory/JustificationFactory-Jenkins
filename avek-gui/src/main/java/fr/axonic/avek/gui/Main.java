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
			String path = ".";
			Enumeration e = getClass().getClassLoader().getResources(path);
			while(e.hasMoreElements())
				l.add(e.nextElement());

			System.err.println("Resources under '"+ path + "': " + l);

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

		primaryStage.setTitle("GUI");
		Scene s = new Scene(root, 800, 600);
		primaryStage.setScene(s);

		s.getStylesheets().add("css/results/jellyBean.css");
		s.getStylesheets().add("css/results/jellyBeanSelector.css");
		s.getStylesheets().add("css/subjects/subjects.css");
		primaryStage.show();
	}
}