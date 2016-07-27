package fr.axonic.avek.gui.components.results;

import fr.axonic.avek.gui.model.sample.ExampleState;
import fr.axonic.avek.gui.util.UtilForTests;
import fr.axonic.avek.model.base.ARangedEnum;
import fr.axonic.avek.model.verification.exception.VerificationException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nathaël N on 07/07/16.
 */
public class TestJellyBeans extends ApplicationTest {
	static { UtilForTests.disableGraphics(); }

	private JellyBean jb;
	private Button jbText;

	@Override
	public void start(Stage stage) throws IOException {
		this.jb = new JellyBean();

		Scene scene = new Scene(jb, 200, 200);
		stage.setScene(scene);
		stage.show();

		jbText = (Button) (jb.getChildren().get(0));
		jb.setId("thejb");
		jbText.setId("jbtext");

		this.jb.setText("The Text");
	}

	@Test
	public void testStateChange() throws VerificationException {
		ARangedEnum<ExampleState> are = new ARangedEnum<>(ExampleState.VERY_LOW);
		List<ExampleState> l = new ArrayList<>();
		Collections.addAll(l, ExampleState.values());
		are.setRange(l);

		this.jb.setStateType(are);

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
