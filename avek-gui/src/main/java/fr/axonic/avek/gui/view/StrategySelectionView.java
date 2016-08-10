package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.Orchestrator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.apache.log4j.Logger;

import java.util.List;

public class StrategySelectionView extends AbstractView {
    private final static Logger LOGGER = Logger.getLogger(StrategySelectionView.class);
    private final static String FXML = "fxml/views/StrategySelectionView.fxml";

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
        Orchestrator.submitChoice(comboBox.getValue());
    }

    public void setAvailableChoices(List<String> choices) {
        try {
            comboBox.setItems(FXCollections.observableArrayList(choices));
        }catch(Exception e) {
            System.err.println("CHOICES="+choices);
            e.printStackTrace();
        }
    }
}
