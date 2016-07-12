package fr.axonic.avek.gui.view.results;

import fr.axonic.avek.gui.model.results.ExampleState;
import fr.axonic.avek.model.base.AEnum;
import fr.axonic.avek.model.base.ARangedEnum;
import fr.axonic.avek.model.verification.exception.VerificationException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nathaël N on 07/07/16.
 */
public class TestJellyBeans extends ApplicationTest {
	static {
		System.setProperty("testfx.robot", "glass");
		System.setProperty("testfx.headless", "true");
		System.setProperty("prism.order", "sw");
		System.setProperty("java.awt.headless", "true");
		System.setProperty("prism.text", "t2k");
	}

	private JellyBean jb;
	private Button jbText, jbCross;

	@Override
	public void start(Stage stage) {
		try {
			this.jb = new JellyBean();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(jb, 200, 200);
		stage.setScene(scene);
		stage.show();

		jbText = (Button)(jb.getChildren().get(0));
		jbCross = (Button)(jb.getChildren().get(1));
		jb.setId("thejb");
		jbText.setId("jbtext");
		jbCross.setId("jbcross");

		this.jb.setText("The Text");
	}

	@Test
	public void testStateChange() throws VerificationException {
		ARangedEnum<ExampleState> aEnum = new ARangedEnum<>(ExampleState.VERY_LOW);
		aEnum.setRange(Arrays.asList(ExampleState.values()));
		this.jb.setStateType(aEnum);

		assertEquals(ExampleState.VERY_LOW, jb.getState());

		clickOn(jbText);
		assertEquals(ExampleState.LOW, jb.getState());


		clickOn(jbText); // Medium
		clickOn(jbText);
		assertEquals(ExampleState.HIGH, jb.getState());

		clickOn(jbText); // Very high
		clickOn(jbText); // Very Low
		clickOn(jbText); // low
		clickOn(jbText);
		assertEquals(ExampleState.MEDIUM, jb.getState());
	}
}
