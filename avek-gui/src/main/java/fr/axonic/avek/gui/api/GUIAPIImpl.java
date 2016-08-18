package fr.axonic.avek.gui.api;

import fr.axonic.avek.gui.view.AbstractView;
import fr.axonic.avek.gui.view.LoadingView;
import fr.axonic.avek.gui.view.frame.MainFrame;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by cduffau on 12/08/16.
 */
public class GUIAPIImpl implements GUIAPI {
    private final static Logger LOGGER = Logger.getLogger(GUIAPIImpl.class);
    private final static GUIAPIImpl INSTANCE = new GUIAPIImpl();

    private final LoadingView loadingView = new LoadingView();
    private MainFrame frame;
    private Map<ComponentType, Object> content;


    public static GUIAPIImpl getInstance() {
        return INSTANCE;
    }


    /**
     * Ask the orchestrator to orchestrate this frame
     * @param frame The Frame to orchestrate
     */
    public void setFrame(MainFrame frame) {
        this.frame = frame;
    }

    /**
     * Setting a loading view up while orchestrator compute for the next view
     */
    public void showLoading() {
        frame.setView(loadingView);
        frame.hideStrategyButton();
    }

    @Override
    public void show(ViewType viewType, Map<ComponentType, Object> content) throws GUIException {
        if (!viewType.isContentCompatible(content)) {
            throw new GUIException("Wrong content for " + viewType);
        }

        this.content = content; // should be done before view loading

        // Setting the view
        try {
            AbstractView view = viewType.getViewClass().newInstance();
            view.load();
            frame.setView(view);
            frame.setStrategyButtonLabel("Treat");
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("Cannot instantiate "+viewType, e);
        }
    }

    public Object getData(ComponentType type) {
        return content.get(type);
    }










    // TODO ------------ DEPRECATED --------------------------

    @Deprecated
    public LoadingView getLoadingView() {
        return loadingView;
    }

    @Deprecated
    public MainFrame getFrame() {
        return frame;
    }
}
