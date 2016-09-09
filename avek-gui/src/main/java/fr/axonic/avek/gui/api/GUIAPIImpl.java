package fr.axonic.avek.gui.api;

import fr.axonic.avek.bus.FeedbackEventEnum;
import fr.axonic.avek.gui.view.AbstractView;
import fr.axonic.avek.gui.view.LoadingView;
import fr.axonic.avek.gui.view.frame.MainFrame;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cduffau on 12/08/16.
 */
public class GUIAPIImpl extends GUIAPI {
    private static final Logger LOGGER = Logger.getLogger(GUIAPIImpl.class);
    private static final GUIAPIImpl INSTANCE = new GUIAPIImpl();

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
    private void showLoading() {
        frame.setView(loadingView);
        frame.hideStrategyButton();
    }

    @Override
    public void show(final String name, final ViewType viewType, Map<ComponentType, Object> content) throws GUIException {
        this.showLoading();

        this.viewType = viewType;

        if (!viewType.isContentCompatible(content)) {
            throw new GUIException("Wrong content for " + viewType);
        }

        // should be done before view loading
        this.content = content;

        // Setting the view
        try {
            AbstractView view = viewType.getViewClass().newInstance();
            view.load();
            frame.setView(view);

            if(viewType == ViewType.STRATEGY_SELECTION_VIEW) {
                frame.hideStrategyButton();
            } else {
                frame.setStrategyButtonLabel(name);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Cannot instantiate "+viewType, e);
        }
    }

    public Object getData(ComponentType type) {
        return content.get(type);
    }

    public void onSubmitPatternChoice(String patternName) {
        Map<FeedbackEventEnum, Object> data = new HashMap<>();
        data.put(FeedbackEventEnum.PATTERN, patternName);
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
        Map<FeedbackEventEnum, Object> data = new HashMap<>();
        data.put(FeedbackEventEnum.STRATEGY, null);
        data.put(FeedbackEventEnum.VIEW_TYPE, viewType);
        data.put(FeedbackEventEnum.CONTENT, content);
        setChanged();
        notifyObservers(data);
    }
}
