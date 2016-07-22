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
import java.util.concurrent.FutureTask;

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
		assertEquals(0, pp.getChildren().size());

		FutureTask ft = new FutureTask<>(() -> { pp.addParameter(new ANumber("LabelText", 42.31)); return true; });
		Platform.runLater(ft);
		ft.get();
		assertEquals(6, pp.getChildren().size());

		ft = new FutureTask<>(() -> { pp.addParameter(new ANumber("2ndLabelText", 12.34)); return true; });
		Platform.runLater(ft);
		ft.get();
		assertEquals(6+6, pp.getChildren().size());

		ft = new FutureTask<>(() -> { pp.addParameter(new ANumber("3rdLabelText", 42)); return true; });
		Platform.runLater(ft);
		ft.get();
		assertEquals(12+6, pp.getChildren().size());

		ft = new FutureTask<>(() -> pp.rmParameter("2ndLabelText"));
		Platform.runLater(ft);
		ft.get();
		assertEquals(18-6, pp.getChildren().size());
	}

	@Test
	public void testDifferentParametersTypes() throws ExecutionException, InterruptedException {
		FutureTask ft = new FutureTask<>(() -> { pp.addParameter(new ANumber("LabelText", 42.31)); return true; });
		Platform.runLater(ft);
		ft.get();
		assertEquals(6, pp.getChildren().size());

		ft = new FutureTask<>(() -> { pp.addParameter(new ABoolean("Boolbool", true)); return true; });
		Platform.runLater(ft);
		ft.get();
		assertEquals(10, pp.getChildren().size());

		ft = new FutureTask<>(() -> { pp.addParameter(new ADate("Datedate", new Date())); return true; });
		Platform.runLater(ft);
		ft.get();
		assertEquals(16, pp.getChildren().size());

		ft = new FutureTask<>(() -> { pp.addParameter(new AString("Strstr", "LaString")); return true; });
		Platform.runLater(ft);
		ft.get();
		assertEquals(20, pp.getChildren().size());
	}
}
