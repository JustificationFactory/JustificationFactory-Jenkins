package fr.axonic.avek.gui.components.jellyBeans;

import fr.axonic.avek.gui.model.sample.ExampleState;
import fr.axonic.avek.gui.model.structure.ExperimentResultsMap;
import fr.axonic.avek.gui.util.UtilForTests;
import fr.axonic.avek.model.base.ARangedEnum;
import fr.axonic.avek.model.verification.exception.VerificationException;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nathaël N on 04/07/16.
 */
public class TestJellyBeanSelector extends ApplicationTest {
	static { UtilForTests.disableGraphics(); }

	private JellyBeanPane jellyBeanPane;


	@Override
	public void start(Stage stage) throws VerificationException, IOException {
		JellyBeansSelector jbs = new JellyBeansSelector();
		Scene scene = new Scene(jbs, 200, 200);
		stage.setScene(scene);
		stage.show();

		ExperimentResultsMap expResMap = new ExperimentResultsMap();
		for (int i = 1; i <= 30; i++) {
			ExampleState val = ExampleState.values()[0];
			ARangedEnum<ExampleState> aEnum = new ARangedEnum<>(val);
			aEnum.setDefaultValue(ExampleState.MEDIUM);
			aEnum.setRange(Arrays.asList(ExampleState.values()));

			expResMap.put("AE" + i, aEnum);
		}

		// Fill experiment sample list
		jbs.setJellyBeansChoice(expResMap);
		jellyBeanPane = jbs.getJellyBeanPane();
	}

	@Test
	public void testSelectItem() {
		assertEquals(0, jellyBeanPane.getChildren().size());

		// Try to add 'null'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.ENTER);
		assertEquals(0, jellyBeanPane.getChildren().size());

		// Move to 'Effect 5'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.ENTER);

		// Add 'Effect 5'
		assertEquals(1, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "AE5");

		// Add 'Effect 5' (should do nothing)
		assertEquals(1, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "AE5");


		// Move to 'Effect 7'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.ENTER);

		// Add 'Effect 7'
		assertEquals(2, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "AE5");
		verifyGoodJellyBean(jellyBeanPane, 1, "AE7");

		// Move to 'Effect 5'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.ENTER);

		// Add 'Effect 5' (should do nothing)
		assertEquals(2, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "AE5");
		verifyGoodJellyBean(jellyBeanPane, 1, "AE7");

		// Move to 'Effect 3'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.ENTER);

		// Add 'Effect 3'
		assertEquals(3, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "AE5");
		verifyGoodJellyBean(jellyBeanPane, 1, "AE7");
		verifyGoodJellyBean(jellyBeanPane, 2, "AE3");
	}

	@Test
	public void testRemoveBean() {
		// add 'Effect 5'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.ENTER);

		// Add 'Effect 7'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.ENTER);

		// Add 'Effect 3'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.ENTER);
		assertEquals(3, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "AE5");
		verifyGoodJellyBean(jellyBeanPane, 1, "AE7");
		verifyGoodJellyBean(jellyBeanPane, 2, "AE3");

		// Remove 'Effect 7'
		JellyBean jb = (JellyBean) jellyBeanPane.getChildren().get(1);

		clickOn(jb.getChildren().get(1));

		assertEquals(2, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "AE5");
		verifyGoodJellyBean(jellyBeanPane, 1, "AE3");

		// Re-add 'Effect 7'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.ENTER);

		assertEquals(3, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "AE5");
		verifyGoodJellyBean(jellyBeanPane, 1, "AE3");
		verifyGoodJellyBean(jellyBeanPane, 2, "AE7");
	}

	private void verifyGoodJellyBean(Pane jellyBeanPane, int i, String name) {
		assertTrue(jellyBeanPane.getChildren().get(i) instanceof JellyBean);
		JellyBean jb = (JellyBean) jellyBeanPane.getChildren().get(i);
		assertEquals(name, jb.getText());
	}
}
