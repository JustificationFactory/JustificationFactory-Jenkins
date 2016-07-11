package fr.axonic.avek.gui.view.results;

import com.google.gson.Gson;
import fr.axonic.avek.gui.model.results.ExampleState;
import fr.axonic.avek.gui.model.results.ExpEffect;
import fr.axonic.avek.model.base.AEnum;
import fr.axonic.avek.model.verification.exception.VerificationException;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.ParentMatchers.hasChildren;

/**
 * Created by Nathaël N on 04/07/16.
 */
public class TestJellyBeanSelector extends ApplicationTest {
	static {
	  	System.setProperty("testfx.robot", "glass");
		System.setProperty("testfx.headless", "true");
		System.setProperty("prism.order", "sw");
		System.setProperty("prism.text", "t2k");
		System.setProperty("java.awt.headless", "true");
	}

	private JellyBeansSelector jbs;
	private Pane jellyBeanPane;


	@Override
	public void start(Stage stage) throws VerificationException {
		try {
			this.jbs = new JellyBeansSelector();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(jbs, 200, 200);
		stage.setScene(scene);
		stage.show();

		List<ExpEffect> expEffects = new ArrayList<>();
		for (int i = 1; i <= 30; i++) {
			String s = "";
			{
				ExampleState val = ExampleState.values()[0];
				AEnum<ExampleState> aEnum = new AEnum(val);
				//aEnum.setDefaultValue(ExampleState.MEDIUM);
				aEnum.setEnumsRange(ExampleState.values());
				s = new Gson().toJson(aEnum);
			}
			AEnum ae = new Gson().fromJson(s, AEnum.class);

			expEffects.add(new ExpEffect(ae, "AE"+i));
		}

		// Fill experiment results list
		jbs.setJellyBeansChoice(FXCollections.observableArrayList(expEffects));
		jellyBeanPane = (Pane) jbs.getChildren().get(1);
	}

	@Test
	public void testSelectItem() {
		verifyThat("#jellyBeansPane", hasChildren(0));
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
		verifyThat("#jellyBeansPane", hasChildren(1));
		assertEquals(1, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "AE5");

		// Add 'Effect 5' (should do nothing)
		verifyThat("#jellyBeansPane", hasChildren(1));
		assertEquals(1, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "AE5");


		// Move to 'Effect 7'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.ENTER);

		// Add 'Effect 7'
		verifyThat("#jellyBeansPane", hasChildren(2));
		assertEquals(2, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "AE5");
		verifyGoodJellyBean(jellyBeanPane, 1, "AE7");

		// Move to 'Effect 5'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.ENTER);

		// Add 'Effect 5' (should do nothing)
		verifyThat("#jellyBeansPane", hasChildren(2));
		assertEquals(2, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "AE5");
		verifyGoodJellyBean(jellyBeanPane, 1, "AE7");

		// Move to 'Effect 3'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.ENTER);

		// Add 'Effect 3'
		verifyThat("#jellyBeansPane", hasChildren(3));
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
