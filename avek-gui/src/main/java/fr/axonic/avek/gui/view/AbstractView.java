package fr.axonic.avek.gui.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nathaël N on 26/07/16.
 */
public abstract class AbstractView extends BorderPane {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractView.class);

    // Is the view loaded ?
    private boolean loaded;

    protected AbstractView() {
        // the view isn't loaded
        loaded = false;
    }

    /**
     * @return true if view was already loaded, false otherwise
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Load the view
     */
    public final void load() {
        // If Already loaded, do nothing
        if (!loaded) {
            this.onLoad();
        }
    }

    /**
     * Called when AbstractView#load is done
     */
    protected abstract void onLoad();

    /**
     * Load a FXML file
     * @throws RuntimeException if called when view was already loaded
     * @param path FXML file path
     */
    protected final void load(String path) {
        // If already loaded
        if (loaded) {
            throw new RuntimeException("Try to load an already loaded view\nFor object: "+toString()+"\nWith path:"+path);
        }

        URL fxml = getClass().getClassLoader().getResource(path);

        FXMLLoader fxmlLoader = new FXMLLoader(fxml);
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
            loaded = true;
        } catch (IOException | RuntimeException e) {
            LOGGER.error("Impossible to load FXML", e);
            throw new RuntimeException("Impossible to load FXML\nFor "+toString()+"\nWith path:"+path);
        }
    }

    // inner-frames size memory (to have the same size before than after to be closed/reopened)
    private final Map<SplitPane, double[]> mementos = new HashMap<>();

    /**
     * Util to show a pane
     * @param index Where the pane will be in his parent (1st position = '0', 2nd = '1'...)
     * @param pane The pane to show
     * @param splitPane The split pane where to show the pane
     * @param button the toggle button that has for state the visibility of the pane
     */
    protected void showPane(int index, Pane pane, SplitPane splitPane, ToggleButton button) {
        splitPane.getItems().add(index, pane);
        pane.setVisible(true);
        pane.setManaged(true);
        button.setSelected(true);
        splitPane.setDividerPositions(mementos.get(splitPane));
    }

    /**
     * Util to hide a pane
     * @param pane The pane to show
     * @param splitPane The split pane where to show the pane
     * @param button the toggle button that has for state the visibility of the pane
     */
    protected void hidePane(Pane pane, SplitPane splitPane, ToggleButton button) {
        mementos.put(splitPane, splitPane.getDividerPositions());
        splitPane.getItems().remove(pane);
        pane.setVisible(false);
        pane.setManaged(false);
        button.setSelected(false);
    }

}
