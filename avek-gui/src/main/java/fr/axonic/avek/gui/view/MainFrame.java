package fr.axonic.avek.gui.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Nathaël N on 26/07/16.
 */
public class MainFrame extends BorderPane {
    private final static Logger LOGGER = Logger.getLogger(MainFrame.class);
    private final static URL FXML = MainFrame.class.getClassLoader()
            .getResource("fxml/views/MainFrame.fxml");

    @FXML
    private Button btnStrategy;

    public MainFrame() {
        FXMLLoader fxmlLoader = new FXMLLoader(FXML);
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        LOGGER.info("Loading MainFrame... (fxml)");
        try {
            fxmlLoader.load();
            LOGGER.debug("MainFrame loaded.");
        } catch (IOException e) {
            LOGGER.fatal("Impossible to load FXML for MainFrame", e);
        }
    }

    @FXML
    private void onClicStrategyButton(ActionEvent event) {
        Orchestrator.onValidate();
    }


    public void setView(AbstractView av) {
        if(!av.isLoaded())
            av.load();
        setCenter(av); // remove abstract view currently loaded
    }
}
