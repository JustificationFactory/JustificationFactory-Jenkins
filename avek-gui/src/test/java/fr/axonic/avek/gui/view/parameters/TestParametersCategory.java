package fr.axonic.avek.gui.view.parameters;

import fr.axonic.avek.gui.view.parameters.list.ParametersGroup;
import fr.axonic.avek.gui.view.parameters.list.ParametersRoot;
import fr.axonic.avek.model.base.ABoolean;
import fr.axonic.avek.model.base.ADate;
import fr.axonic.avek.model.base.ANumber;
import fr.axonic.avek.model.base.AString;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nathaël N on 11/07/16.
 */
public class TestParametersCategory extends ApplicationTest {
	static {
		System.setProperty("testfx.robot", "glass");
		System.setProperty("prism.order", "sw");
		System.setProperty("testfx.headless", "true");
		System.setProperty("java.awt.headless", "true");
		System.setProperty("prism.text", "t2k");
	}

	private ParametersGroup pp;

	@Override
	public void start(Stage stage) throws IOException {
		this.pp = new ParametersRoot();

		Scene scene = new Scene(pp, 500, 300);
		stage.setScene(scene);
		stage.show();
	}

	@Test
	public void testAddRemoveParameters() throws ExecutionException, InterruptedException {
		assertEquals(3, pp.getChildren().size());

		Platform.runLater(() -> pp.addParameter(new ANumber("LabelText", 42.31)));
		assertEquals(3+6, pp.getChildren().size());

		Platform.runLater(() -> pp.addParameter(new ANumber("2ndLabelText", 12.34)));
		assertEquals(9+6, pp.getChildren().size());

		Platform.runLater(() -> pp.addParameter(new ANumber("3rdLabelText", 42)));
		assertEquals(15+6, pp.getChildren().size());

		Platform.runLater(() -> pp.rmParameter("2ndLabelText"));
		assertEquals(21-6, pp.getChildren().size());
	}

	@Test
	public void testDifferentParametersTypes() throws ExecutionException, InterruptedException {
		Platform.runLater(() -> pp.addParameter(new ANumber("LabelText", 42.31)));
		assertEquals(3+6, pp.getChildren().size());

		Platform.runLater(() -> pp.addParameter(new ABoolean("Boolbool", true)));
		assertEquals(9+4, pp.getChildren().size());

		Platform.runLater(() -> pp.addParameter(new ADate("Datedate", new Date())));
		assertEquals(13+6, pp.getChildren().size());

		Platform.runLater(() -> pp.addParameter(new AString("Strstr", "LaString")));
		assertEquals(17+4, pp.getChildren().size());
	}
}
