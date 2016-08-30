package fr.axonic.avek.gui;

import fr.axonic.avek.bus.Orchestrator;
import fr.axonic.avek.engine.WrongEvidenceException;
import fr.axonic.avek.gui.api.GUIAPIImpl;
import fr.axonic.avek.gui.api.GUIException;
import fr.axonic.avek.gui.view.frame.MainFrame;
import fr.axonic.validation.exception.VerificationException;
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
    public void start(Stage primaryStage) throws IOException, WrongEvidenceException, GUIException, VerificationException {
        LOGGER.debug("Loading MainFrame...");

        primaryStage.setTitle("#AVEK analyzer");

        MainFrame mainFrame = new MainFrame();
        Scene s = new Scene(mainFrame);
        primaryStage.setScene(s);

        primaryStage.show();
        LOGGER.debug("MainFrame created.");

        GUIAPIImpl.getInstance().setFrame(mainFrame);
        new Orchestrator(GUIAPIImpl.getInstance());
    }
}