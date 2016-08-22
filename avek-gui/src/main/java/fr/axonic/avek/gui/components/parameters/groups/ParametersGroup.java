package fr.axonic.avek.gui.components.parameters.groups;

import fr.axonic.avek.gui.components.parameters.ExpParameterLeaf;
import fr.axonic.avek.gui.components.parameters.IExpParameter;
import fr.axonic.avek.gui.components.parameters.MyNode;
import fr.axonic.avek.gui.components.parameters.ParameterLine;
import fr.axonic.base.engine.*;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;

/**
 * Created by Nathaël N on 13/07/16.
 */
public abstract class ParametersGroup extends VBox implements IExpParameter {
    private static final Logger LOGGER = Logger.getLogger(ParametersGroup.class);
    private static final String CSS = "fr/axonic/avek/gui/components/parameters/parameters.css";

    protected final int level;
    private final ExpParameterLeaf title;

    ParametersGroup(final int level) { this(level, null); }
    /**
     * @param level Deep level of this parameter grid (= his parent level+1)
     */
    ParametersGroup(final int level, ExpParameterLeaf title) {
        this.level = level;

        this.title = title;
        if(title != null) {
            title.getLabelTitle().getStyleClass().add("category-title");
        }

        LOGGER.info("Adding parameters.css");
        this.getStylesheets().add(CSS);

        getStyleClass().add("parameter-group");
    }

    /**
     * @param aEntity The AVar to add as a Experiment parameter,
     *                or the AList to add as a Experiment parameter sub group
     * @throws ClassCastException is the parameter is not a AVar
     *                            nor a AList (of AList and AVar)
     */
    private void addParameter(AEntity aEntity) {
        if(aEntity instanceof AStructure) {
            addCategory(AVarHelper.transformAStructureToAList((AStructure) aEntity));
        } else if(aEntity instanceof AList) {
            addCategory((AList) aEntity);
        } else if(aEntity instanceof AVar) {
            addLeaf((AVar) aEntity); // throws ClassCastException if not a AVar
        } else {
            LOGGER.error("Impossible cast object to AStruct, AList or AVar: "+aEntity);
        }
    }

    protected void addCategory(AList aList) {
        ParametersGroup subCategory = new ParametersCategory(level + 1, aList);

        // Adding to the GUI
        addExpParameter(subCategory);
    }

    protected void addLeaf(AVar aVar) {
        addExpParameter(new ExpParameterLeaf(level + 1, aVar));
    }

    final void addExpParameter(IExpParameter subParam) {
        // Adding graphical elements to the GUI
        this.getChildren().add(subParam.getParameterLine());

        ParameterLine.computeSizes();
    }

    public void setAList(AList<?> list) {
        getChildren().clear();

        // Adding sub elements
        if(title != null) { // Reset the title
            this.getChildren().add(title.getParameterLine());
        }

        // Set children
        list.getList().forEach(this::addParameter);
    }

    private ParameterLine parameterLine;
    @Override
    public ParameterLine getParameterLine() {
        if(parameterLine == null) {
            parameterLine = new ParameterLine();
            parameterLine.addNode(new MyNode(this), "GROUP", 0);
        }

        return parameterLine;
    }

    ExpParameterLeaf getCategoryTitle() {
        return title;
    }
}
