package fr.axonic.avek.gui.api;

import fr.axonic.avek.gui.view.AbstractView;
import fr.axonic.avek.gui.view.LoadingView;
import fr.axonic.avek.gui.view.frame.MainFrame;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * Created by cduffau on 12/08/16.
 */
public class GUIAPIImpl extends GUIAPI {
    private final static Logger LOGGER = Logger.getLogger(GUIAPIImpl.class);
    private final static GUIAPIImpl INSTANCE = new GUIAPIImpl();

    private final LoadingView loadingView = new LoadingView();
    private Map<ComponentType, Object> content;
    private MainFrame frame;
    private ViewType viewType;


    public static GUIAPIImpl getInstance() {
        return INSTANCE;
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
        this.viewType = viewType;

        if (!viewType.isContentCompatible(content)) {
            throw new GUIException("Wrong content for " + viewType);
        }

        this.content = content; // should be done before view loading

        // Setting the view
        try {
            AbstractView view = viewType.getViewClass().newInstance();
            view.load();
            frame.setView(view);

            if(viewType == ViewType.STRATEGY_SELECTION_VIEW) {
                frame.hideStrategyButton();
            }
            else {
                frame.setStrategyButtonLabel(viewType.name());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("Cannot instantiate "+viewType, e);
        }
    }

    public Object getData(ComponentType type) {
        return content.get(type);
    }

    public void onSubmitPatternChoice(String patternName) {
        Map<String, Object> data = new HashMap<>();
        data.put("Pattern", patternName);
        setChanged();
        notifyObservers(data);
    }

    public void initializeFrame(Stage primaryStage) {
        LOGGER.debug("Loading MainFrame...");

        primaryStage.setTitle("#AVEK analyzer");

        this.frame = new MainFrame();
        Scene s = new Scene(frame);
        primaryStage.setScene(s);

        primaryStage.show();

        LOGGER.debug("MainFrame created.");
    }


    public void onStrategySubmitted() {
        Map<String, Object> data = new HashMap<>();
        data.put("Strategy", null);
        data.put("ViewType", viewType);
        data.put("Content", content);
        setChanged();
        notifyObservers(data);
    }
}
