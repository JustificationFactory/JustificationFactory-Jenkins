package fr.axonic.avek.gui.view.expEffects;

import fr.axonic.avek.gui.model.BooleanExpEffect;
import fr.axonic.avek.gui.model.IExpEffect;
import fr.axonic.avek.gui.model.StringExpEffect;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.ParentMatchers.hasChildren;

/**
 * Created by Nathaël N on 04/07/16.
 */
public class TestJellyBeans2 extends GuiTest {
	/* Using org.loadui.testfx */


	@Override
	protected Parent getRootNode() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		try (InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream("fxml/gui.fxml")) {
			return fxmlLoader.load(inputStream);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}



	@Before
	public void before() throws InterruptedException {
		stage.getScene().getStylesheets().add("css/expEffects/jellyBean.css");
		stage.getScene().getStylesheets().add("css/expEffects/jellyBeanSelector.css");
		stage.getScene().getStylesheets().add("css/expSubject/expSubject.css");

		JellyBeansSelector jbs = find("#jellyBeansSelector");

		List<IExpEffect> expEffects = new ArrayList<>();
		for (int i = 1; i <= 30; i++)
			expEffects.add(i%3==1?
					new StringExpEffect("SEffect " + i):
					new BooleanExpEffect("BEffect " + i));

		// Fill experiment effects list
		jbs.setJellyBeansChoice(FXCollections.observableArrayList(expEffects));
	}

	@Test
	public void testSelectItem() {
		FlowPane jellyBeanPane = find("#jellyBeansPane");

		verifyThat("#jellyBeansPane", hasChildren(0));

		// Move to 'Effect 5'
		click("#comboBoxJellyBean")
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.ENTER);


		// Add 'Effect 5'
		click("#addJellyBeanButton");
		verifyThat("#jellyBeansPane", hasChildren(1));
		verifyGoodJellyBean(jellyBeanPane, 0, "BEffect 5");

		// Add 'Effect 5' (should do nothing)
		click("#addJellyBeanButton");
		verifyThat("#jellyBeansPane", hasChildren(1));
		verifyGoodJellyBean(jellyBeanPane, 0, "BEffect 5");

		// Move to 'Effect 7'
		click("#comboBoxJellyBean")
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.ENTER);

		// Add 'Effect 7'
		click("#addJellyBeanButton");
		verifyThat("#jellyBeansPane", hasChildren(2));
		verifyGoodJellyBean(jellyBeanPane, 0, "BEffect 5");
		verifyGoodJellyBean(jellyBeanPane, 1, "SEffect 7");

		// Move to 'Effect 5'
		click("#comboBoxJellyBean")
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.ENTER);

		// Add 'Effect 5' (should do nothing)
		click("#addJellyBeanButton");
		verifyThat("#jellyBeansPane", hasChildren(2));
		verifyGoodJellyBean(jellyBeanPane, 0, "BEffect 5");
		verifyGoodJellyBean(jellyBeanPane, 1, "SEffect 7");

		// Move to 'Effect 3'
		click("#comboBoxJellyBean")
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.ENTER);

		// Add 'Effect 3'
		click("#addJellyBeanButton");
		verifyThat("#jellyBeansPane", hasChildren(3));
		verifyGoodJellyBean(jellyBeanPane, 0, "BEffect 5");
		verifyGoodJellyBean(jellyBeanPane, 1, "SEffect 7");
		verifyGoodJellyBean(jellyBeanPane, 2, "BEffect 3");
	}

	@Test
	public void testRemoveBean() {
		// add 'Effect 5'
		click("#comboBoxJellyBean")
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.ENTER)
				.click("#addJellyBeanButton");

		// Add 'Effect 7'
		click("#comboBoxJellyBean")
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.ENTER)
				.click("#addJellyBeanButton");

		// Add 'Effect 3'
		click("#comboBoxJellyBean")
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.ENTER)
				.click("#addJellyBeanButton");
		verifyThat("#jellyBeansPane", hasChildren(3));

		// Remove 'Effect 7'
		Pane jellyBeanPane = find("#jellyBeansPane");
		JellyBean jb = (JellyBean) jellyBeanPane.getChildren().get(1);
		move(jb).move((Node)find(".jellyBeanCross", jb))
				.click((Node)find(".jellyBeanCross", jb));

		verifyThat("#jellyBeansPane", hasChildren(2));
		verifyGoodJellyBean(jellyBeanPane, 0, "BEffect 5");
		verifyGoodJellyBean(jellyBeanPane, 1, "BEffect 3");

		// Re-add 'Effect 7'
		click("#comboBoxJellyBean")
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.ENTER)
				.click("#addJellyBeanButton");

		click("#addJellyBeanButton");

		sleep(1000);
		System.out.println(jellyBeanPane.getChildren());
		verifyThat("#jellyBeansPane", hasChildren(3));
		verifyGoodJellyBean(jellyBeanPane, 0, "BEffect 5");
		verifyGoodJellyBean(jellyBeanPane, 1, "BEffect 3");
		verifyGoodJellyBean(jellyBeanPane, 2, "SEffect 7");
	}


	private void verifyGoodJellyBean(Pane jellyBeanPane, int i, String name) {
		assertTrue(jellyBeanPane.getChildren().get(i) instanceof JellyBean);
		JellyBean jb = (JellyBean) jellyBeanPane.getChildren().get(i);
		assertEquals(name, jb.getExpEffect().getName());
	}
}
