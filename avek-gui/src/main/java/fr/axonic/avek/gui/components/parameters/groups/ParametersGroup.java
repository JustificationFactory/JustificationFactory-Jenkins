package fr.axonic.avek.gui.components.parameters.groups;

import fr.axonic.avek.gui.components.parameters.ExpParameterLeaf;
import fr.axonic.avek.gui.components.parameters.IExpParameter;
import fr.axonic.base.engine.*;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Nathaël N on 13/07/16.
 */
public abstract class ParametersGroup extends GridPane implements IExpParameter {
    private static final Logger LOGGER = Logger.getLogger(ParametersGroup.class);
    private static final String CSS = "css/components/parameters.css";

    protected final int level;
    private AList<AEntity> element;
    private final List<IExpParameter> subElements;
    private final ExpParameterLeaf title;


    ParametersGroup(final int level) { this(level, null); }
    /**
     * @param level Deep level of this parameter grid (= his parent level+1)
     */
    ParametersGroup(final int level, ExpParameterLeaf title) {
        subElements = new ArrayList<>();
        this.level = level;

        this.title = title;
        if(title != null) {
            title.getLabelTitle().getStyleClass().add("category-title");
        }

		/*
        [ Levelmark ][ Title & value            ]
		     ↓       Main ParametersGrid's title
			 |↓      ParametersGrid's title
			 ||      A subelement : value
			 ||      A subelement : value
			 |       Another element : value
		 */

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
        }
		/*if(aEntity instanceof AList<AEntity>)
			addCategory(aEntity);
		else if(aEntity instanceof AVar)
			addLeaf(aEntity);*/
		else {
		    try {
                addCategory((AList<AEntity>) aEntity);
            } catch (ClassCastException cce) {
                try {
                    addLeaf((AVar) aEntity); // throws ClassCastException if not a AVar
                }catch(Exception e) {
                    LOGGER.error("Impossible cast object to AVar: "+aEntity, e);
                }
            }
        }
    }

    protected void addCategory(AList<AEntity> aList) {
        ParametersGroup subCategory = new ParametersCategory(level + 1, aList);

        // Adding to the GUI
        addExpParameter(subCategory);
    }

    protected void addLeaf(AVar aVar) {
        addExpParameter(new ExpParameterLeaf(level + 1, aVar));
    }

    final void addExpParameter(IExpParameter subParam) {
        int rowIndex = subElements.size();

        // Adding graphical elements to the GUI
        for (Node n : subParam.getElements()) {
            GridPane.setRowIndex(n, rowIndex);
            this.getChildren().add(n);
        }

        // Adding to the list
        subElements.add(subParam);
    }

    public void setAList(AList<AEntity> list) {
        this.element = list;

        subElements.clear();
        getChildren().clear();

        // Adding sub elements
        if(title != null) {
            title.getElements().forEach(getChildren()::add);
            subElements.add(title);
        }
        element.getList().forEach(this::addParameter);
    }

    @Override
    public Set<Node> getElements() {
        Set<Node> s = new HashSet<>();
        s.add(this);
        return s;
    }

    ExpParameterLeaf getCategoryTitle() {
        return title;
    }
}
