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
        Orchestrator.waitforOrchestrating();
        assertTrue("Application cannot be launched", true);
        Orchestrator.onValidate(); // Click on Strategy button ("Treat")
        Orchestrator.waitforOrchestrating();
        Orchestrator.submitChoice("Establish Effect");
        Orchestrator.waitforOrchestrating();
        Orchestrator.onValidate();
        Orchestrator.waitforOrchestrating();
        Orchestrator.submitChoice("Generalize");
        Orchestrator.waitforOrchestrating();
        Orchestrator.onValidate();
        Orchestrator.waitforOrchestrating();
        assertTrue(true);
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
