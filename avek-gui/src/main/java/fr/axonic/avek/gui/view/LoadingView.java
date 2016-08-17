package fr.axonic.avek.gui.view;

import org.apache.log4j.Logger;

/**
 * Created by Nathaël N on 04/08/16.
 */
public class LoadingView extends AbstractView {
    private final static Logger LOGGER = Logger.getLogger(LoadingView.class);
    private final static String FXML = "fr/axonic/avek/gui/view/LoadingView.fxml";

    @Override
    protected void onLoad() {
        // Loading loading view.......
        super.load(FXML);
    }
}
