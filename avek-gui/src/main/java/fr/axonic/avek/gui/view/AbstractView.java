package fr.axonic.avek.gui.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Nathaël N on 26/07/16.
 */
public abstract class AbstractView extends BorderPane {
    private final static Logger LOGGER = Logger.getLogger(AbstractView.class);

    private boolean loaded;

    AbstractView() {
        loaded = false;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public final void load() {
        if (loaded) {
            return;
        }

        this.onLoad();
    }

    protected abstract void onLoad();

    protected final void load(String path) {
        if (loaded) {
            return;
        }

        URL fxml = getClass().getClassLoader().getResource(path);

        FXMLLoader fxmlLoader = new FXMLLoader(fxml);
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
            loaded = true;
        } catch (IOException | RuntimeException e) {
            LOGGER.fatal("Impossible to load FXML", e);
        }
    }

}
