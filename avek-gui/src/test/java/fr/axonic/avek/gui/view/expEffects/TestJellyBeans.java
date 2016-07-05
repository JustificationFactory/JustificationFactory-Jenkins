package fr.axonic.avek.gui.view.expEffects;

import fr.axonic.avek.gui.model.IExpEffect;
import fr.axonic.avek.gui.model.expEffects.BooleanExpEffect;
import fr.axonic.avek.gui.model.expEffects.EnumExpEffect;
import fr.axonic.avek.gui.model.expEffects.StringExpEffect;
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
public class TestJellyBeans extends ApplicationTest {
	/*
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
			expEffects.add(i%3==0?new StringExpEffect("SEffect " + i):
					i%3==1?new BooleanExpEffect("BEffect " + i):
							new EnumExpEffect("EEffect " + i));

		// Fill experiment effects list
		jbs.setJellyBeansChoice(FXCollections.observableArrayList(expEffects));
	}

	@Test
	public void testSelectItem() {
		FlowPane jellyBeanPane = find("#jellyBeansPane");

		assertEquals(0, jellyBeanPane.getChildren().size());

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
		assertEquals(1, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "EEffect 5");

		// Add 'Effect 5' (should do nothing)
		click("#addJellyBeanButton");
		assertEquals(1, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "EEffect 5");

		// Move to 'Effect 7'
		click("#comboBoxJellyBean")
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.ENTER);

		// Add 'Effect 7'
		click("#addJellyBeanButton");
		assertEquals(2, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "EEffect 5");
		verifyGoodJellyBean(jellyBeanPane, 1, "BEffect 7");

		// Move to 'Effect 5'
		click("#comboBoxJellyBean")
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.ENTER);

		// Add 'Effect 5' (should do nothing)
		click("#addJellyBeanButton");
		assertEquals(2, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "EEffect 5");
		verifyGoodJellyBean(jellyBeanPane, 1, "BEffect 7");

		// Move to 'Effect 3'
		click("#comboBoxJellyBean")
				.push(KeyCode.UP)
				.push(KeyCode.UP)
				.push(KeyCode.ENTER);

		// Add 'Effect 3'
		click("#addJellyBeanButton");
		assertEquals(3, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "EEffect 5");
		verifyGoodJellyBean(jellyBeanPane, 1, "BEffect 7");
		verifyGoodJellyBean(jellyBeanPane, 2, "SEffect 3");
	}

	@Test
	public void testRemoveBean() {
		Pane jellyBeanPane = find("#jellyBeansPane");

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
		assertEquals(3, jellyBeanPane.getChildren().size());

		// Remove 'Effect 7'
		JellyBean jb = (JellyBean) jellyBeanPane.getChildren().get(1);
		move(jb).move(find(".jellyBeanCross", jb))
				.click((Node)find(".jellyBeanCross", jb));

		assertEquals(2, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "EEffect 5");
		verifyGoodJellyBean(jellyBeanPane, 1, "SEffect 3");

		// Re-add 'Effect 7'
		click("#comboBoxJellyBean")
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.DOWN)
				.push(KeyCode.ENTER)
				.click("#addJellyBeanButton");

		click("#addJellyBeanButton");

		assertEquals(3, jellyBeanPane.getChildren().size());
		verifyGoodJellyBean(jellyBeanPane, 0, "EEffect 5");
		verifyGoodJellyBean(jellyBeanPane, 1, "SEffect 3");
		verifyGoodJellyBean(jellyBeanPane, 2, "BEffect 7");
	}


	private void verifyGoodJellyBean(Pane jellyBeanPane, int i, String name) {
		assertTrue(jellyBeanPane.getChildren().get(i) instanceof JellyBean);
		JellyBean jb = (JellyBean) jellyBeanPane.getChildren().get(i);
		assertEquals(name, jb.getExpEffect().getName());
	}
	*/


	@Override
	public void start(Stage stage) {
		JellyBeansSelector jbs = new JellyBeansSelector();
		Scene scene = new Scene(jbs, 800, 600);
		stage.setScene(scene);
		stage.show();

		List<IExpEffect> expEffects = new ArrayList<>();
		for (int i = 1; i <= 30; i++)
			expEffects.add(i % 3 == 0 ? new StringExpEffect("SEffect " + i) :
					i % 3 == 1 ? new BooleanExpEffect("BEffect " + i) :
							new EnumExpEffect("EEffect " + i));

		// Fill experiment effects list
		jbs.setJellyBeansChoice(FXCollections.observableArrayList(expEffects));
	}

	@Test
	public void testSelectItem() {
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
