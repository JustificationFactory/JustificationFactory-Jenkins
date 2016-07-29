package fr.axonic.avek.gui.components.parameters;

import fr.axonic.avek.gui.util.ConcurrentTaskManager;
import fr.axonic.avek.gui.util.UtilForTests;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nathaël N on 25/07/16.
 */
public class TestGeneralizedParametersPane { /*extends ApplicationTest {
	static { UtilForTests.disableGraphics(); }

	private GeneralizedParametersPane pp;

	@Override
	public void start(Stage stage) throws IOException {
		this.pp = new GeneralizedParametersPane();

		Scene scene = new Scene(pp, 500, 300);
		stage.setScene(scene);
		stage.show();
	}

	@Test
	public void testOnClickHide() throws Exception {
		ConcurrentTaskManager ctm = new ConcurrentTaskManager();

		assertTrue(pp.getComplementaryFilePane().isVisible());
		assertTrue(pp.getAdditionalInfoPane().isVisible());

		ctm.runNowOnPlatform(() -> pp.onHideComplementaryFiles(null));
		assertFalse(pp.getComplementaryFilePane().isVisible());
		assertTrue(pp.getAdditionalInfoPane().isVisible());

		ctm.runNowOnPlatform(() -> pp.onHideAdditionalInfo(null));
		assertFalse(pp.getComplementaryFilePane().isVisible());
		assertFalse(pp.getAdditionalInfoPane().isVisible());

		ctm.runNowOnPlatform(() -> pp.onHideComplementaryFiles(null));
		assertTrue(pp.getComplementaryFilePane().isVisible());
		assertFalse(pp.getAdditionalInfoPane().isVisible());

		ctm.runNowOnPlatform(() -> pp.onHideAdditionalInfo(null));
		assertTrue(pp.getComplementaryFilePane().isVisible());
		assertTrue(pp.getAdditionalInfoPane().isVisible());
	}*/
}
