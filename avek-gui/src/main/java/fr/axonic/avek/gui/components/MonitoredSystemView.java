package fr.axonic.avek.gui.components;

import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.base.engine.AVar;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.Map;
import java.util.Set;

/**
 * Created by Nathaël N on 04/07/16.
 */
public class MonitoredSystemView extends Accordion {
	public void setMonitoredSystem(MonitoredSystem ms) {
		getPanes().clear();

		Map<String, Set<AVar>> map = ms.getMap();
		for (String category : map.keySet()) {
			ScrollPane sp = new ScrollPane();
			ListView<Label> list = new ListView<>();
			list.setMaxWidth(99999);

			getPanes().add(new TitledPane(category, sp));
			sp.setContent(list);
			sp.setFitToWidth(true);
			sp.setFitToHeight(true);

			for (AVar av : map.get(category))
				list.getItems().add(new Label(av.getLabel() + " : " + av.getValue().toString()));
		}
	}
}
