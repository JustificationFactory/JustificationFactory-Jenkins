package fr.axonic.avek.gui.view.parameters.list;

import fr.axonic.avek.model.base.engine.AList;
import fr.axonic.avek.model.base.engine.AVar;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathaël N on 13/07/16.
 */
public class ParametersCategory extends GridPane implements IExpParameter {
	private final static Logger logger = Logger.getLogger(ParametersCategory.class);
	private final static String PARAMETERS_CSS = "css/parameters/parameters.css";

	private final CheckBox markedUtil;
	private final Label label;
	private final LevelMark levelMark;

	private final int level;
	List<IExpParameter> expParameters;

	/**
	 * Define a sub ParametersGrid
	 * @param level Deep level of this parameter grid (= his parent level+1)
	 * @param title Title of the ParametersGrid
	 */
	private ParametersCategory(final int level, final String title) {
		expParameters = new ArrayList<>();
		this.level = level+1;

		/*
		[ checkbox ][ Levelmark ][ Title & ...  ]
		     []          ↓       Main ParametersGrid's title
			 []          |↓      ParametersGrid's title
			 []          ||      A subelement : value ...
			 []          ||      A subelement : value ...
			 []          |       Another element : value ...
		 */

		// Generating GUI component
		markedUtil = new CheckBox();
		markedUtil.setSelected(true);
		markedUtil.setOnAction(this::onClickMarkedUtil);
		GridPane.setColumnIndex(markedUtil, 0);
		GridPane.setRowIndex(markedUtil, 0);

		levelMark = new LevelMark(level, true);
		GridPane.setColumnIndex(levelMark, 1);
		GridPane.setRowIndex(levelMark, 0);

		label = new Label();
		label.setText(title);
		label.getStyleClass().add("category_title");
		GridPane.setColumnIndex(label, 2);
		GridPane.setRowIndex(label, 0);

		// Adding GUI component
		getChildren().add(markedUtil);
		getChildren().add(levelMark);
		getChildren().add(label);

		this.setVgap(2);

		logger.info("Adding parameters.css");
		this.getStylesheets().add(PARAMETERS_CSS);
	}

	/**
	 * Define a ParametersCategory as Root
	 */
	public ParametersCategory() {
		this(0, "");
		markedUtil.setVisible(false);
		levelMark.setVisible(false);
		label.setVisible(false);
	}

	public boolean addParamCategory(AList<AVar> alist) {
		ParametersCategory epg = new ParametersCategory(level, alist.getLabel());

		// Adding sub elements
		alist.getEntities().forEach(epg::addExpParameter);

		// Adding to the GUI
		GridPane.setColumnSpan(epg, 6);
		GridPane.setRowIndex(epg, expParameters.size()+1); // +1 because of title line
		this.getChildren().add(epg);

		// Adding to the list
		return expParameters.add(epg);
	}
	public boolean addExpParameter(AVar avar) {
		ExpParameter ep = new ExpParameter(avar, level);

		int i = expParameters.size()+1; // +1 because of title line

		// Adding graphical elements to the GUI
		for (Node n : ep.getElements()) {
			GridPane.setRowIndex(n, i);
			this.getChildren().add(n);
		}

		// Adding to the list
		return expParameters.add(ep);
	}

	public synchronized IExpParameter rmParameter(String name) {
		// searching parameter index
		int indexToRm = -1;

		// int j=0+1 because of title line at index 0
		for (int j = 1; j < expParameters.size(); j++)
			if (expParameters.get(j).getName().equals(name)) {
				indexToRm = j;
				break;
			}

		if(indexToRm==-1)
			return null;

		// Removing from view & shift view
		for(Node n : new ArrayList<>(getChildren())) {
			int index = GridPane.getRowIndex(n);

			if(indexToRm == index)
				getChildren().remove(n);
			else if(index > indexToRm)
				GridPane.setRowIndex(n, index-1);
		}

		// Removing parameter from list
		return expParameters.remove(indexToRm);
	}

	private void onClickMarkedUtil(ActionEvent event) {
		boolean b = markedUtil.isSelected();

		label.setDisable(!b);

		// Removing from view & shift view
		for(Node n : getChildren()) {
			int index = GridPane.getRowIndex(n);

			if (index > 0) {
				n.setVisible(b);
				n.setManaged(b);
			}
		}
	}

	@Override
	public String getName() {
		return null;
	}
}
