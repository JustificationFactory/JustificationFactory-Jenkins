package fr.axonic.avek.gui.components.jellybeans;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

/**
 * Created by Nathaël N on 28/06/16.
 */
@SuppressWarnings("WeakerAccess")
public class JellyBean<T,U,V> extends HBox {
    private static final Logger LOGGER = LoggerFactory.getLogger(JellyBean.class);
    private static final URL FXML
            = JellyBean.class.getClassLoader().getResource("fr/axonic/avek/gui/components/jellybeans/JellyBean.fxml");

    @FXML
    private Button jbLabel;
    @FXML
    private Button jbCross;

    private Consumer<JellyBean> onDeleteObserver;
    private final Tooltip tooltip;

    private boolean isRemovable;
    private JellyBeanItem<T,U,V> jellyBeanItem;

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
            LOGGER.error("Impossible to load FXML for JellyBean", e);
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
        jellyBeanItem.nextState();
    }

    private void onStateChanged(U lastState, U newState) {
        // Computing tooltip
        final String tooltipText = jellyBeanItem.toPrettyString();

        final String strLastState = jellyBeanItem.getStateCode(lastState).toLowerCase();
        final String strNewState = jellyBeanItem.getStateCode(newState).toLowerCase();
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
        return "JellyBean=" + jellyBeanItem;
    }

    void setOnDelete(Consumer<JellyBean> onDelete) {
        this.onDeleteObserver = onDelete;
        setRemovable(onDelete != null);
    }

    private void setRemovable(boolean removable) {
        isRemovable = removable;
        jbCross.setVisible(removable);
        jbCross.setManaged(removable);
    }


    public void set(JellyBeanItem<T,U,V> item) {
        this.jellyBeanItem = item;
        jbLabel.setText(item.getLabel());

        item.addStateChangeListener(this::onStateChanged);
        item.setEditableStateChangeListener(this::onEditableChanged);
    }

    JellyBeanItem<T,U,V> getJellyBeanItem() {
        return jellyBeanItem;
    }
}
