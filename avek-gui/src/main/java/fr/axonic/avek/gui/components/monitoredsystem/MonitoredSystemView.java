package fr.axonic.avek.gui.components.monitoredsystem;

import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.base.ADate;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AVar;
import javafx.scene.control.*;

import java.text.SimpleDateFormat;
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
                // Pretty print dates
                if(av instanceof ADate) {
                    SimpleDateFormat df = new SimpleDateFormat();
                    df.applyPattern("dd/MM/yyyy HH:mm:ss");
                    list.getItems().add(new Label(
                            av.getLabel() + " : " + (((ADate)av).getValue()==null?null:df.format(((ADate)av).getValue().getTime()))));
                } else {
                    list.getItems().add(new Label(
                            av.getLabel() + " : " + ((AVar)av).getValue()));
                }
	        }
        }
    }
}
