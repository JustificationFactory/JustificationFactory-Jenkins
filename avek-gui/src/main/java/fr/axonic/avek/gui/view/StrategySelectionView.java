package fr.axonic.avek.gui.view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.function.Consumer;

public class StrategySelectionView extends AbstractView {
    private final static Logger LOGGER = Logger.getLogger(StrategySelectionView.class);
    private final static String FXML = "fxml/views/StrategySelectionView.fxml";

    @FXML
    private Button submit;
    @FXML
    private ComboBox<ViewOrchestrator> comboBox;

    private Consumer<ViewOrchestrator> onSetViewMethod;

    @Override
    protected void onLoad() {
        LOGGER.info("Loading StrategySelectionView...");
        super.load(FXML);
        LOGGER.debug("StrategySelectionView loaded.");
    }

    @FXML
    void onSubmit(ActionEvent event) {
        setView(comboBox.getValue());
    }

    void setAvailableChoices(List<ViewOrchestrator> views) {
        comboBox.setItems(FXCollections.observableArrayList(views));
    }

    private void setView(ViewOrchestrator view) {
        onSetViewMethod.accept(view);
    }

    void onSetView(Consumer<ViewOrchestrator> onSetViewMethod) {
        this.onSetViewMethod = onSetViewMethod;
    }
}

