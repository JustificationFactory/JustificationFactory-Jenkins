package fr.axonic.avek.gui.components.jellybeans;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

/**
 * Created by Nathaël N on 28/06/16.
 */
public class JellyBean<T> extends HBox {
    private final static Logger LOGGER = Logger.getLogger(JellyBean.class);
    private final static URL FXML
            = JellyBean.class.getClassLoader().getResource("fxml/components/JellyBean.fxml");

    @FXML
    private Button jbLabel;
    @FXML
    private Button jbCross;

    private Consumer<JellyBean<T>> onDeleteObserver;
    private final Tooltip tooltip;

    private boolean isRemovable;
    private JellyBeanItem<T> item;

    // should be public
    public JellyBean() {
        FXMLLoader fxmlLoader = new FXMLLoader(FXML);
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        LOGGER.debug("Loading JellyBean... (fxml)");
        try {
            fxmlLoader.load();
            LOGGER.debug("JellyBean loaded.");
        } catch (IOException e) {
            LOGGER.fatal("Impossible to load FXML for JellyBean", e);
        }

        tooltip = new Tooltip();

        this.getStylesheets().add("css/components/results/levels.css");
        this.getStylesheets().add("css/components/results/boolean.css");
        LOGGER.debug("Added css for JellyBean");
    }

    @FXML
    public void initialize() {
        jbLabel.getStyleClass().remove("button"); // remove base css
        jbCross.getStyleClass().remove("button");
        setRemovable(false);
    }

    @FXML
    public void onClickOnCross(ActionEvent actionEvent) {
        if (isRemovable) {
            onDeleteObserver.accept(this);
        }
    }

    @FXML
    public void onClickOnLabel(ActionEvent actionEvent) {
        item.nextState();
    }

    private void onStateChanged(T lastState, T newState) {
        final String strLastState = lastState.toString();
        final String strNewState = newState.toString();

        // Computing tooltip
        String allStates = "";
        for(T s : item.getStates())
            allStates += s.toString().equalsIgnoreCase(strNewState)?", ["+s+"]":(", "+s);

        allStates = allStates.substring(2).toUpperCase();

        final String tooltipText = "Current state: " + strNewState.toUpperCase() + "\n"
                + "All states: " + allStates;

        // Computing style
        Platform.runLater(() -> {
            jbLabel.getStyleClass().remove(strLastState.toLowerCase());
            jbCross.getStyleClass().remove(strLastState.toLowerCase());
            jbLabel.getStyleClass().add(strNewState.toLowerCase());
            jbCross.getStyleClass().add(strNewState.toLowerCase());

            tooltip.setText(tooltipText);
            jbLabel.setTooltip(tooltip);
        });
    }
    private void onEditableChanged(boolean editable) {
        getStyleClass().remove("jellyBeanEditable");
        if (editable) {
            getStyleClass().add("jellyBeanEditable");
        }
    }

    @Override
    public String toString() {
        return "JellyBean=" + item;
    }

    void setOnDelete(Consumer<JellyBean<T>> onDelete) {
        this.onDeleteObserver = onDelete;
        setRemovable(onDelete != null);
    }

    private void setRemovable(boolean removable) {
        isRemovable = removable;
        jbCross.setVisible(removable);
        jbCross.setManaged(removable);
    }


    public void set(JellyBeanItem<T> item) {
        this.item = item;
        jbLabel.setText(item.getText());

        item.addStateChangeListener(this::onStateChanged);
        item.setEditableStateChangeListener(this::onEditableChanged);
        item.setState(0);
    }

    public JellyBeanItem<T> getItem() {
        return item;
    }
}
