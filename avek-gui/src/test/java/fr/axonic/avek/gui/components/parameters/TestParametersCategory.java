package fr.axonic.avek.gui.components.parameters;

import fr.axonic.avek.gui.components.parameters.list.ParametersGroup;
import fr.axonic.avek.gui.components.parameters.list.ParametersRoot;
import fr.axonic.avek.gui.util.ConcurrentTaskManager;
import fr.axonic.avek.gui.util.UtilForTests;
import fr.axonic.avek.model.base.ABoolean;
import fr.axonic.avek.model.base.ADate;
import fr.axonic.avek.model.base.ANumber;
import fr.axonic.avek.model.base.AString;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nathaël N on 11/07/16.
 */
public class TestParametersCategory extends ApplicationTest {
	static { UtilForTests.disableGraphics(); }

	private ParametersGroup pp;

	@Override
	public void start(Stage stage) throws IOException {
		System.out.println("BEFORE");
		this.pp = new ParametersRoot();

		Scene scene = new Scene(pp, 500, 300);
		stage.setScene(scene);
		stage.show();
		System.out.println("after BEFORE");
	}

	@Test
	public void testAddRemoveParameters() throws Exception {
		System.out.println("CALL");
		ConcurrentTaskManager ctm = new ConcurrentTaskManager();

		assertEquals(0, pp.getChildren().size());
		System.out.println("CALL2");
		ctm.runNowOnPlatform(() -> pp.addParameter(new ANumber("LabelText", 42.31)));
		System.out.println("CALL3");
		assertEquals(6, pp.getChildren().size());

		ctm.runNowOnPlatform(() -> pp.addParameter(new ANumber("2ndLabelText", 12.34)));
		assertEquals(6 + 6, pp.getChildren().size());

		ctm.runNowOnPlatform(() -> pp.addParameter(new ANumber("3rdLabelText", 42)));
		assertEquals(12 + 6, pp.getChildren().size());

		ctm.runNowOnPlatform(() -> pp.rmParameter("2ndLabelText"));
		assertEquals(18 - 6, pp.getChildren().size());
	}

	@Test
	public void testDifferentParametersTypes() throws Exception {
		ConcurrentTaskManager ctm = new ConcurrentTaskManager();

		ctm.runNowOnPlatform(() -> pp.addParameter(new ANumber("LabelText", 42.31)));

		assertEquals(6, pp.getChildren().size());

		ctm.runNowOnPlatform(() -> pp.addParameter(new ABoolean("Boolbool", true)));
		assertEquals(10, pp.getChildren().size());

		ctm.runNowOnPlatform(() -> pp.addParameter(new ADate("Datedate", new Date())));
		assertEquals(16, pp.getChildren().size());

		ctm.runNowOnPlatform(() -> pp.addParameter(new AString("Strstr", "LaString")));
		assertEquals(20, pp.getChildren().size());
	}
}
