package fr.axonic.avek.gui;

import fr.axonic.avek.gui.util.ConcurrentTaskManager;
import fr.axonic.avek.gui.util.UtilForTests;
import fr.axonic.avek.gui.view.frame.MainFrame;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertTrue;

/**
 * Created by Nathaël N on 11/08/16.
 */
public class TestOrchestrator extends ApplicationTest {
    static {
        UtilForTests.disableGraphics();
    }

    @Test
    public void test() throws Exception {
        ConcurrentTaskManager ctm = new ConcurrentTaskManager();
        ctm.runNowOnPlatform(() -> Orchestrator.setFrame(new MainFrame()));
        Orchestrator.waitForOrchestrating();
        assertTrue("Application cannot be launched", true);
        Orchestrator.onValidate(); // Click on Strategy button ("Treat")
        Orchestrator.waitForOrchestrating();
        Orchestrator.submitChoice("Establish Effect");
        Orchestrator.waitForOrchestrating();
        Orchestrator.onValidate();
        Orchestrator.waitForOrchestrating();
        Orchestrator.submitChoice("Generalize");
        Orchestrator.waitForOrchestrating();
        Orchestrator.onValidate();
        Orchestrator.waitForOrchestrating();
        assertTrue(true);
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
