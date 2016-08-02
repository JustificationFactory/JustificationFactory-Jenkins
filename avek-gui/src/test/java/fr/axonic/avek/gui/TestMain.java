package fr.axonic.avek.gui;

import fr.axonic.avek.gui.util.ConcurrentTaskManager;
import fr.axonic.avek.gui.util.UtilForTests;
import fr.axonic.avek.gui.util.ViewOrchestrator;
import fr.axonic.avek.gui.view.EstablishEffectView;
import fr.axonic.avek.gui.view.GeneralizeView;
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

		ViewOrchestrator oNull = new ViewOrchestrator(null, "Strategy chooser");
		ViewOrchestrator o3 = new ViewOrchestrator(new GeneralizeView(), "Generalize");
		o3.addFollowing(oNull);
		ViewOrchestrator o2 = new ViewOrchestrator(new EstablishEffectView(), "Establish effects");
		o2.addFollowing(o3);
		ViewOrchestrator o1 = new ViewOrchestrator(new TreatView(), "Treat");
		o1.addFollowing(o2);

		oNull.addFollowing(o1);
		oNull.addFollowing(o2);
		oNull.addFollowing(o3);

		ctm.runNowOnPlatform(() -> reference.getMainFrame().setView(oNull));
		assertTrue(true);

/*		ctm.runNowOnPlatform(() -> reference.getMainFrame().setView(o1));
		assertTrue(true);

		ctm.runNowOnPlatform(() -> reference.getMainFrame().setView(o2));
		assertTrue(true);
*/
		ctm.runNowOnPlatform(() -> reference.getMainFrame().setView(o3));
		assertTrue(true);
	}
}
