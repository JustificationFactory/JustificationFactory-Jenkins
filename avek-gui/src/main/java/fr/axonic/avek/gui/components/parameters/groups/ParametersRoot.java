package fr.axonic.avek.gui.components.parameters.groups;

import fr.axonic.base.engine.AList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Nathaël N on 21/07/16.
 */
public class ParametersRoot extends GridPane {
    private static final Logger LOGGER = Logger.getLogger(ParametersCategory.class);
    private static final String CSS = "fr/axonic/avek/gui/components/parameters/parameters.css";

    private final ParametersCategory parameters;

    // Should be public to be accessed from FXML
    public ParametersRoot() {
        this(new ParametersCategory(0));
    }

    ParametersRoot(ParametersCategory generalizedCategory) {
        parameters = generalizedCategory;
        LOGGER.info("Adding parameters.css");
        this.getStylesheets().add(CSS);

        getStyleClass().add("parameter-root");
    }

    public void setAList(AList<?> list) {
        // Empty current display
        getChildren().clear();

        // Compute category
        parameters.setAList(list);

        // Set display
        List<List<Node>> nodes = parameters.getNodes();
        // line begin at 1 to ignore "root"
        for(int line=1;line<nodes.size();line++) {
            for(Node n : nodes.get(line)) {
                GridPane.setRowIndex(n, line);
                // ColumnIndex/ColumnSpan set in sub components
                getChildren().add(n);
            }
        }
    }

    public ParametersCategory getParametersCategory() {
        return parameters;
    }
}
