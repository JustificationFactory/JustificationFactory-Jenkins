package fr.axonic.avek.gui;

import fr.axonic.avek.gui.util.ConcurrentTaskManager;
import fr.axonic.avek.gui.util.Util;
import fr.axonic.avek.gui.util.UtilForTests;
import fr.axonic.avek.gui.view.EstablishEffectView;
import fr.axonic.avek.gui.view.GeneralizedView;
import fr.axonic.avek.gui.view.TreatView;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertTrue;

/**
 * Created by Nathaël N on 27/07/16.
 */
public class TestMain extends ApplicationTest {
	static { UtilForTests.disableGraphics(); }

	private Main reference;

	@Override
	public void start(Stage stage) throws Exception {
		this.reference = new Main();
		reference.start(stage);
	}

	@Test
	public void mainTest() {
		ConcurrentTaskManager ctm = new ConcurrentTaskManager();

		GeneralizedView gView = new GeneralizedView();
		ctm.runLaterOnPlatform(() -> reference.getMainFrame().setView(gView));
		ctm.runLaterOnPlatform(() -> gView.setMonitoredSystem(Util.getFileContent("files/subjectFile.json")));
		ctm.runLaterOnPlatform(() -> gView.setExperimentParameters(Util.getFileContent("files/parametersFile.json")));
		ctm.waitForTasks();
		assertTrue(true);

		EstablishEffectView eeView = new EstablishEffectView();
		ctm.runLaterOnPlatform(() -> reference.getMainFrame().setView(eeView));
		ctm.runLaterOnPlatform(() -> eeView.setMonitoredSystem(Util.getFileContent("files/subjectFile.json")));
		ctm.runLaterOnPlatform(() -> eeView.setExperimentParameters(Util.getFileContent("files/parametersFile.json")));
		ctm.waitForTasks();
		assertTrue(true);

		TreatView tView = new TreatView();
		ctm.runLaterOnPlatform(() -> reference.getMainFrame().setView(tView));
		ctm.runLaterOnPlatform(() -> tView.setMonitoredSystem(Util.getFileContent("files/subjectFile.json")));
		ctm.runLaterOnPlatform(() -> tView.setExperimentParameters(Util.getFileContent("files/parametersFile.json")));
		ctm.waitForTasks();
		assertTrue(true);
	}
}
