package fr.axonic.avek.gui.view.parameters;

import fr.axonic.avek.gui.model.results.ExampleState;
import fr.axonic.avek.gui.view.results.JellyBean;
import fr.axonic.avek.model.base.AEnum;
import fr.axonic.avek.model.base.ANumber;
import fr.axonic.avek.model.base.AVar;
import fr.axonic.avek.model.verification.exception.VerificationException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
	public void start(Stage stage) {
		try {
			this.pp = new ParametersPane();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(pp, 200, 200);
		stage.setScene(scene);
		stage.show();
	}

	@Test
	public void testAddRemoveParameters() throws ExecutionException, InterruptedException {
		assertEquals(5, pp.getChildren().size());

		pp.addParameter(new ANumber("LabelText", 42.31));
		assertEquals(10, pp.getChildren().size());

		pp.addParameter(new ANumber("2ndLabelText", 12.34));
		assertEquals(15, pp.getChildren().size());

		pp.addParameter(new ANumber("3rdLabelText", 42));
		assertEquals(20, pp.getChildren().size());

		pp.rmParameter("2ndLabelText");
		assertEquals(15, pp.getChildren().size());
	}

	@Test
	public void testDifferentParametersTypes() {

	}
}
