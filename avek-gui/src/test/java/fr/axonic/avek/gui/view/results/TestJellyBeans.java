package fr.axonic.avek.gui.view.results;

import fr.axonic.avek.gui.model.results.ExempleState;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

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
		this.jb = new JellyBean();

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
	public void testStateChange() {
		this.jb.setStateType(ExempleState.class);
		assertEquals(ExempleState.VERY_LOW, jb.getState());

		clickOn(jbText);
		assertEquals(ExempleState.LOW, jb.getState());


		clickOn(jbText); // Medium
		clickOn(jbText);
		assertEquals(ExempleState.HIGH, jb.getState());

		clickOn(jbText); // Very high
		clickOn(jbText); // Very Low
		clickOn(jbText); // low
		clickOn(jbText);
		assertEquals(ExempleState.MEDIUM, jb.getState());
	}
}
