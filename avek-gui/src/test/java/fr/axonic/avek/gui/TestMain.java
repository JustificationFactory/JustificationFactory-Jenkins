package fr.axonic.avek.gui;

import fr.axonic.avek.gui.util.ConcurrentTaskManager;
import fr.axonic.avek.gui.util.UtilForTests;
import fr.axonic.avek.gui.util.ViewOrchestrator;
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
	public void mainTest() throws Exception {
		ConcurrentTaskManager ctm = new ConcurrentTaskManager();

		ViewOrchestrator oNull = new ViewOrchestrator(null);
		GeneralizedView gView = new GeneralizedView();
		ViewOrchestrator o3 = new ViewOrchestrator(gView);
		o3.addFollowing(oNull);
		EstablishEffectView eeView = new EstablishEffectView();
		ViewOrchestrator o2 = new ViewOrchestrator(eeView);
		o2.addFollowing(o3);
		TreatView tView = new TreatView();
		ViewOrchestrator o1 = new ViewOrchestrator(tView);
		o1.addFollowing(o2);

		oNull.addFollowing(o1);
		oNull.addFollowing(o2);
		oNull.addFollowing(o3);

		ctm.runNowOnPlatform(() -> reference.getMainFrame().setView(oNull));
		assertTrue(true);

		ctm.runNowOnPlatform(() -> reference.getMainFrame().setView(o1));
		assertTrue(true);

		ctm.runNowOnPlatform(() -> reference.getMainFrame().setView(o2));
		assertTrue(true);

		ctm.runNowOnPlatform(() -> reference.getMainFrame().setView(o3));
		assertTrue(true);
	}
}
