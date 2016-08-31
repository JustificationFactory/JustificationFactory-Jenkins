package fr.axonic.avek.gui;

import fr.axonic.avek.bus.Orchestrator;
import fr.axonic.avek.engine.WrongEvidenceException;
import fr.axonic.avek.gui.api.GUIAPIImpl;
import fr.axonic.avek.gui.api.GUIException;
import fr.axonic.validation.exception.VerificationException;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, WrongEvidenceException, GUIException, VerificationException {
        GUIAPIImpl.getInstance().initializeFrame(primaryStage);
        new Orchestrator(GUIAPIImpl.getInstance());
    }
}