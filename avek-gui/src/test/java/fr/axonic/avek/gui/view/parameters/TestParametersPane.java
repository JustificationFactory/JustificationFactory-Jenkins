package fr.axonic.avek.gui.view.parameters;

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
public class TestParametersPane extends ApplicationTest {
	static {
		System.setProperty("testfx.robot", "glass");
		System.setProperty("prism.order", "sw");
		System.setProperty("testfx.headless", "true");
		System.setProperty("java.awt.headless", "true");
		System.setProperty("prism.text", "t2k");
	}

	private ParametersPane pp;

	@Override
	public void start(Stage stage) throws IOException {
		this.pp = new ParametersPane();

		Scene scene = new Scene(pp, 500, 300);
		stage.setScene(scene);
		stage.show();
	}

	@Test
	public void testAddRemoveParameters() throws ExecutionException, InterruptedException {
		assertEquals(3, pp.getChildren().size());

		FutureTask ft = new FutureTask<>(() -> pp.addExpParameter(new ANumber("LabelText", 42.31)));
		Platform.runLater(ft);
		ft.get();
		assertEquals(3+6, pp.getChildren().size());

		ft = new FutureTask<>(() -> pp.addExpParameter(new ANumber("2ndLabelText", 12.34)));
		Platform.runLater(ft);
		ft.get();
		assertEquals(9+6, pp.getChildren().size());

		ft = new FutureTask<>(() -> pp.addExpParameter(new ANumber("3rdLabelText", 42)));
		Platform.runLater(ft);
		ft.get();
		assertEquals(15+6, pp.getChildren().size());

		ft = new FutureTask<>(() -> pp.rmParameter("2ndLabelText"));
		Platform.runLater(ft);
		ft.get();
		assertEquals(21-6, pp.getChildren().size());
	}

	@Test
	public void testDifferentParametersTypes() throws ExecutionException, InterruptedException {
		FutureTask ft = new FutureTask<>(() -> pp.addExpParameter(new ANumber("LabelText", 42.31)));
		Platform.runLater(ft);
		ft.get();
		assertEquals(3+6, pp.getChildren().size());

		ft = new FutureTask<>(() -> pp.addExpParameter(new ABoolean("Boolbool", true)));
		Platform.runLater(ft);
		ft.get();
		assertEquals(9+4, pp.getChildren().size());

		ft = new FutureTask<>(() -> pp.addExpParameter(new ADate("Datedate", new Date())));
		Platform.runLater(ft);
		ft.get();
		assertEquals(13+4, pp.getChildren().size());

		ft = new FutureTask<>(() -> pp.addExpParameter(new AString("Strstr", "LaString")));
		Platform.runLater(ft);
		ft.get();
		assertEquals(17+4, pp.getChildren().size());
	}
}
