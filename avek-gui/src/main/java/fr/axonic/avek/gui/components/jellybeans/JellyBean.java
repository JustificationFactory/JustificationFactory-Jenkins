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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by Nathaël N on 28/06/16.
 */
public class JellyBean extends HBox {
    private final static Logger LOGGER = Logger.getLogger(JellyBean.class);
    private final static URL FXML
            = JellyBean.class.getClassLoader().getResource("fxml/components/JellyBean.fxml");

    @FXML
    private Button jbLabel;
    @FXML
    private Button jbCross;

    private List<String> states;
    private int stateIndex = 0;
    private final Set<Consumer<String>> onChangeObservers;
    private Consumer<JellyBean> onDeleteObserver;
    private final Tooltip tooltip;

    private boolean isRemovable, isEditable;

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

        onChangeObservers = new HashSet<>();
        tooltip = new Tooltip();

        this.getStylesheets().add("css/components/results/levels.css");
        this.getStylesheets().add("css/components/results/boolean.css");
        LOGGER.debug("Added css for JellyBean");
    }

    @FXML
    public void initialize() {
        jbLabel.getStyleClass().remove("button"); // remove base css
        jbCross.getStyleClass().remove("button");
        setEditable(false);
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
        // ReadOnly
        if (isEditable) {
            setState((stateIndex + 1) % states.size());
        }
    }

    private synchronized void setState(int state) {
        String before = states.get(stateIndex).toLowerCase();
        stateIndex = state;
        String after = states.get(stateIndex).toLowerCase();
        LOGGER.debug("State set to "+after);


        String allStates = "";
        for(String s : states)
            allStates += s.equalsIgnoreCase(after)?", ["+s+"]":(", "+s);
        allStates = allStates.substring(2).toUpperCase();

        final String tooltipText = "Current stateIndex: " + after.toUpperCase() + "\n"
                                 + "All states: " + allStates;
        Platform.runLater(() -> {
            jbLabel.getStyleClass().remove(before);
            jbCross.getStyleClass().remove(before);
            jbLabel.getStyleClass().add(after);
            jbCross.getStyleClass().add(after);

            tooltip.setText(tooltipText);
            jbLabel.setTooltip(tooltip);
        });
        for(Consumer<String> c : onChangeObservers) {
            c.accept(this.getState());
        }
    }


    String getState() {
        return states.get(stateIndex);
    }

    void setStates(List<String> states) {
        this.states = states;
        setState(0);
    }

    void setState(String state) {
        this.stateIndex = states.indexOf(state);
    }

    public void setText(String text) {
        jbLabel.setText(text);
    }

    public String getText() {
        return jbLabel.getText();
    }

    @Override
    public String toString() {
        return "JellyBean=" + getState();
    }

    void setOnDelete(Consumer<JellyBean> onDelete) {
        this.onDeleteObserver = onDelete;
        setEditable(onDelete != null);
        setRemovable(onDelete != null);
    }

    private void setRemovable(boolean removable) {
        isRemovable = removable;
        jbCross.setVisible(removable);
        jbCross.setManaged(removable);
    }

    void setEditable(boolean editable) {
        isEditable = editable;

        getStyleClass().remove("jellyBeanEditable");
        if (editable) {
            getStyleClass().add("jellyBeanEditable");
        }
    }

    void addListener(Consumer<String> onStateChange) {
        onChangeObservers.add(onStateChange);
    }
}
