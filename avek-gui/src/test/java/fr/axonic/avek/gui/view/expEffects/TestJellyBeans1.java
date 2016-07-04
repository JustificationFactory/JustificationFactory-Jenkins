package fr.axonic.avek.gui.view.expEffects;

import fr.axonic.avek.gui.model.BooleanExpEffect;
import fr.axonic.avek.gui.model.IExpEffect;
import fr.axonic.avek.gui.model.StringExpEffect;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.ParentMatchers.hasChildren;

/**
 * Created by Nathaël N on 04/07/16.
 */
public class TestJellyBeans1 extends /*GuiTest*/ ApplicationTest {
	/* USING org.testfx */

	@Override
		public void start(Stage stage) {
		JellyBeansSelector jbs = new JellyBeansSelector();
		Scene scene = new Scene(jbs, 800, 600);
		stage.setScene(scene);
		stage.show();

		List<IExpEffect> expEffects = new ArrayList<>();
		for (int i = 1; i <= 30; i++)
			expEffects.add(i%3==1?
					new StringExpEffect("SEffect " + i):
					new BooleanExpEffect("BEffect " + i));

		// Fill experiment effects list
		jbs.setJellyBeansChoice(FXCollections.observableArrayList(expEffects));
	}

	@Test
	public void selectItem() {
		verifyThat("#jellyBeansPane", hasChildren(0));

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

		// Add 'Effect 5' (should do nothing)
		clickOn("#addJellyBeanButton");
		verifyThat("#jellyBeansPane", hasChildren(1));


		// Move to 'Effect 7'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.ENTER);

		// Add 'Effect 7'
		clickOn("#addJellyBeanButton");
		verifyThat("#jellyBeansPane", hasChildren(2));

		// Move to 'Effect 5'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.ENTER);

		// Add 'Effect 5' (should do nothing)
		clickOn("#addJellyBeanButton");
		verifyThat("#jellyBeansPane", hasChildren(2));

		// Move to 'Effect 3'
		clickOn("#comboBoxJellyBean")
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.ENTER);

		// Add 'Effect 3'
		clickOn("#addJellyBeanButton");
		verifyThat("#jellyBeansPane", hasChildren(3));
	}
}
