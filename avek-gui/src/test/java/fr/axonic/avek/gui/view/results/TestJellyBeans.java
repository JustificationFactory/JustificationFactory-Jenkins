package fr.axonic.avek.gui.view.results;

import fr.axonic.avek.gui.model.results.ExempleState;
import fr.axonic.avek.gui.model.results.ExempleStateBool;
import fr.axonic.avek.gui.model.results.ExpEffect;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.ParentMatchers.hasChildren;

/**
 * Created by Nathaël N on 04/07/16.
 */
public class TestJellyBeans extends ApplicationTest {

	static {

		System.setProperty("testfx.robot", "glass");
		System.setProperty("testfx.headless", "true");
		System.setProperty("prism.order", "sw");
		System.setProperty("prism.text", "t2k");
		System.setProperty("java.awt.headless", "true");

	}

	private JellyBeansSelector jbs;

	@Override
	public void start(Stage stage) {
		this.jbs = new JellyBeansSelector();
		Scene scene = new Scene(jbs, 800, 600);
		stage.setScene(scene);
		stage.show();

		List<ExpEffect> expEffects = new ArrayList<>();
		for (int i = 1; i <= 9; i++) {
			expEffects.add(new ExpEffect(ExempleState.class, "ES1-"+i));
			expEffects.add(new ExpEffect(ExempleStateBool.class, "ESB-"+i));
			expEffects.add(new ExpEffect(ExempleState.class, "ES2-"+i));
		}

		// Fill experiment results list
		jbs.setJellyBeansChoice(FXCollections.observableArrayList(expEffects));
	}

	@Test
	public void testSelectItem() {
		Pane jellyBeanPane = (Pane) jbs.getChildren().get(1); // JellyBeansPane

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
		clickOn("#addJellyBeanButton");
		verifyThat("#jellyBeansPane", hasChildren(1));
		assertEquals(1, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "ESB-2");

		// Add 'Effect 5' (should do nothing)
		clickOn("#addJellyBeanButton");
		verifyThat("#jellyBeansPane", hasChildren(1));
		assertEquals(1, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "ESB-2");


		// Move to 'Effect 7'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.ENTER);

		// Add 'Effect 7'
		clickOn("#addJellyBeanButton");
		verifyThat("#jellyBeansPane", hasChildren(2));
		assertEquals(2, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "ESB-2");
		verifyGoodJellyBean(jellyBeanPane, 1, "ES1-3");

		// Move to 'Effect 5'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.ENTER);

		// Add 'Effect 5' (should do nothing)
		clickOn("#addJellyBeanButton");
		verifyThat("#jellyBeansPane", hasChildren(2));
		assertEquals(2, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "ESB-2");
		verifyGoodJellyBean(jellyBeanPane, 1, "ES1-3");

		// Move to 'Effect 3'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.ENTER);

		// Add 'Effect 3'
		clickOn("#addJellyBeanButton");
		verifyThat("#jellyBeansPane", hasChildren(3));
		assertEquals(3, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "ESB-2");
		verifyGoodJellyBean(jellyBeanPane, 1, "ES1-3");
		verifyGoodJellyBean(jellyBeanPane, 2, "ES2-1");
	}

	@Test
	public void testRemoveBean() {
		Pane jellyBeanPane = (Pane) jbs.getChildren().get(1); // JellyBeansPane

		// add 'Effect 5'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.ENTER)
				.clickOn("#addJellyBeanButton");

		// Add 'Effect 7'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.ENTER)
				.clickOn("#addJellyBeanButton");

		// Add 'Effect 3'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.ENTER)
				.clickOn("#addJellyBeanButton");
		assertEquals(3, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "ESB-2");
		verifyGoodJellyBean(jellyBeanPane, 1, "ES1-3");
		verifyGoodJellyBean(jellyBeanPane, 2, "ES2-1");

		// Remove 'Effect 7'
		JellyBean jb = (JellyBean) jellyBeanPane.getChildren().get(1);

		clickOn(jb.getChildren().get(1));

		assertEquals(2, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "ESB-2");
		verifyGoodJellyBean(jellyBeanPane, 1, "ES2-1");

		// Re-add 'Effect 7'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.ENTER)
				.clickOn("#addJellyBeanButton");

		assertEquals(3, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "ESB-2");
		verifyGoodJellyBean(jellyBeanPane, 1, "ES2-1");
		verifyGoodJellyBean(jellyBeanPane, 2, "ES1-3");
	}

	private void verifyGoodJellyBean(Pane jellyBeanPane, int i, String name) {
		assertTrue(jellyBeanPane.getChildren().get(i) instanceof JellyBean);
		JellyBean jb = (JellyBean) jellyBeanPane.getChildren().get(i);
		assertEquals(name, jb.getText());
	}
}
