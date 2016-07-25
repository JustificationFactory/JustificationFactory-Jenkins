package fr.axonic.avek.gui.view.parameters;

import fr.axonic.avek.gui.Main;
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
public class TestParametersPane extends ApplicationTest {
	static { Main.disableGraphics(); }

	private ParametersPane pp;

	@Override
	public void start(Stage stage) throws IOException {
		this.pp = new ParametersPane();

		Scene scene = new Scene(pp, 500, 300);
		stage.setScene(scene);
		stage.show();
	}

	@Test
	public void testOnClickHide() {
		assertTrue(pp.getComplementaryFilePane().isVisible());
		assertTrue(pp.getAdditionalInfoPane().isVisible());

		pp.onHideComplementaryFiles(null);
		assertFalse(pp.getComplementaryFilePane().isVisible());
		assertTrue(pp.getAdditionalInfoPane().isVisible());

		pp.onHideAdditionalInfo(null);
		assertFalse(pp.getComplementaryFilePane().isVisible());
		assertFalse(pp.getAdditionalInfoPane().isVisible());

		pp.onHideComplementaryFiles(null);
		assertTrue(pp.getComplementaryFilePane().isVisible());
		assertFalse(pp.getAdditionalInfoPane().isVisible());

		pp.onHideAdditionalInfo(null);
		assertTrue(pp.getComplementaryFilePane().isVisible());
		assertTrue(pp.getAdditionalInfoPane().isVisible());
	}
}
