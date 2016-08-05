package fr.axonic.avek.gui.components;

import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AVar;
import javafx.scene.control.*;

import java.util.Set;

/**
 * Created by Nathaël N on 04/07/16.
 */
public class MonitoredSystemView extends Accordion {
    public void setMonitoredSystem(MonitoredSystem ms) {
        getPanes().clear();

        Set<AList<AEntity>> map = ms.getCategories();
        for (AList<AEntity> category : map) {
            ScrollPane sp = new ScrollPane();
            ListView<Label> list = new ListView<>();
            list.setMaxWidth(Integer.MAX_VALUE);

            getPanes().add(new TitledPane(category.getLabel(), sp));
            sp.setContent(list);
            sp.setFitToWidth(true);
            sp.setFitToHeight(true);

	        for (AEntity av : category) {
		        list.getItems().add(new Label(av.getLabel() + " : " + ((AVar)av).getValue().toString()));
	        }
        }
    }
}
