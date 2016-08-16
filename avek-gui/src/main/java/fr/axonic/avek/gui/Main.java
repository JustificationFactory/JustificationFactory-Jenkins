package fr.axonic.avek.gui;

import fr.axonic.avek.gui.view.frame.MainFrame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public class Main extends Application {
    private final static Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        LOGGER.debug("Loading MainFrame...");

        primaryStage.setTitle("#AVEK analyzer");

        MainFrame mainFrame = new MainFrame();
        Scene s = new Scene(mainFrame);
        primaryStage.setScene(s);

        primaryStage.show();
        LOGGER.debug("MainFrame created.");

        Orchestrator.setFrame(mainFrame);
    }
}