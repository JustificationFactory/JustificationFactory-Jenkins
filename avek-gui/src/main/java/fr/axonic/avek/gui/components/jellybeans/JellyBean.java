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
public class JellyBean<T,U> extends HBox {
    private final static Logger LOGGER = Logger.getLogger(JellyBean.class);
    private final static URL FXML
            = JellyBean.class.getClassLoader().getResource("fr/axonic/avek/gui/components/jellybeans/JellyBean.fxml");

    @FXML
    private Button jbLabel;
    @FXML
    private Button jbCross;

    private Consumer<JellyBean<T,U>> onDeleteObserver;
    private final Tooltip tooltip;

    private boolean isRemovable;
    private JellyBeanItem<T,U> item;

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

        this.getStylesheets().add("fr/axonic/avek/gui/components/parameters/levels.css");
        this.getStylesheets().add("fr/axonic/avek/gui/components/parameters/boolean.css");
        LOGGER.debug("Added css for JellyBean");
    }

    @FXML
    public void initialize() {
        jbLabel.getStyleClass().remove("button"); // remove base css
        jbCross.getStyleClass().remove("button");
        setRemovable(false);

        this.setMinWidth(this.getPrefWidth());
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

    private void onStateChanged(ItemStateFormat<U> lastState, ItemStateFormat<U> newState) {
        LOGGER.debug("State of '"+item.getText()+"' changed from '"+lastState.getValue()+"' to '"+newState.getValue()+"'");

        // Computing tooltip
        String allStates = "";
        for(ItemStateFormat<U> s : item.getStates()) {
            if(s.equals(newState)) {
                allStates += ", ["+s.getLabel()+"]";
            }else {
                allStates += ", "+s.getLabel();
            }
        }
        allStates = allStates.substring(2); // remove the first ", " of the String
        final String tooltipText = "Current state: " + newState.getLabel() + "\n"
                + "All states: " + allStates;


        final String strLastState = lastState.getValue().toLowerCase();
        final String strNewState = newState.getValue().toLowerCase();
        // Computing style
        Platform.runLater(() -> {
            jbLabel.getStyleClass().remove(strLastState);
            jbCross.getStyleClass().remove(strLastState);
            jbLabel.getStyleClass().add(strNewState);
            jbCross.getStyleClass().add(strNewState);

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

    void setOnDelete(Consumer<JellyBean<T,U>> onDelete) {
        this.onDeleteObserver = onDelete;
        setRemovable(onDelete != null);
    }

    private void setRemovable(boolean removable) {
        isRemovable = removable;
        jbCross.setVisible(removable);
        jbCross.setManaged(removable);
    }


    public void set(JellyBeanItem<T,U> item) {
        this.item = item;
        jbLabel.setText(item.getText());

        item.addStateChangeListener(this::onStateChanged);
        item.setEditableStateChangeListener(this::onEditableChanged);
    }

    JellyBeanItem<T,U> getItem() {
        return item;
    }
}
