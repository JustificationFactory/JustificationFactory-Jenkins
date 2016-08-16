package fr.axonic.avek.gui.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Nathaël N on 26/07/16.
 */
public abstract class AbstractView extends BorderPane {
    private final static Logger LOGGER = Logger.getLogger(AbstractView.class);

    private final Set<AbstractView> orchestrator;


    private boolean loaded;

    protected AbstractView() {
        loaded = false;
        orchestrator = new LinkedHashSet<>();
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



    private final Map<SplitPane, double[]> mementos = new HashMap<>();

    protected void showPane(int index, Pane pane, SplitPane splitPane, ToggleButton button) {
        splitPane.getItems().add(index, pane);
        pane.setVisible(true);
        pane.setManaged(true);
        button.setSelected(true);
        splitPane.setDividerPositions(mementos.get(splitPane));
    }

    protected void hidePane(Pane pane, SplitPane splitPane, ToggleButton button) {
        mementos.put(splitPane, splitPane.getDividerPositions());
        splitPane.getItems().remove(pane);
        pane.setVisible(false);
        pane.setManaged(false);
        button.setSelected(false);
    }

}
