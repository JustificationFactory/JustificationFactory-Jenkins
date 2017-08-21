package fr.axonic.avek.gui.view.strategyselection;

import fr.axonic.avek.gui.api.ComponentType;
import fr.axonic.avek.gui.api.GUIAPIImpl;
import fr.axonic.avek.gui.view.AbstractView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StrategySelectionView extends AbstractView {
    private static final Logger LOGGER = LoggerFactory.getLogger(StrategySelectionView.class);
    private static final String FXML = "fr/axonic/avek/gui/view/strategyselection/StrategySelectionView.fxml";

    @FXML
    private Button submit;
    @FXML
    private ComboBox<String> comboBox;

    @Override
    protected void onLoad() {
        LOGGER.info("Loading StrategySelectionView...");
        super.load(FXML);
        LOGGER.debug("StrategySelectionView loaded.");
    }

    @FXML
    void onSubmit(ActionEvent event) {
        GUIAPIImpl.getInstance().onSubmitPatternChoice(comboBox.getValue());
    }

    @FXML
    public void initialize() {
        @SuppressWarnings("unchecked")
        List<String> choices = (List<String>) GUIAPIImpl.getInstance().getData(ComponentType.SELECTION);
        try {
            comboBox.setItems(FXCollections.observableArrayList(choices));
        }catch(NullPointerException e) {
            LOGGER.error("An unknown error occurred. DEBUG: CHOICES="+choices, e);
        }
    }
}
