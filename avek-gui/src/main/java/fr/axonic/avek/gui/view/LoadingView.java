package fr.axonic.avek.gui.view;

import org.apache.log4j.Logger;

/**
 * Created by Nathaël N on 04/08/16.
 */
public class LoadingView extends AbstractView {
    private final static Logger LOGGER = Logger.getLogger(LoadingView.class);
    private final static String FXML = "fxml/views/LoadingView.fxml";

    @Override
    protected void onLoad() {
        LOGGER.info("Loading LoadingView...");
        super.load(FXML);
        LOGGER.debug("LoadingView loaded.");
    }
}
