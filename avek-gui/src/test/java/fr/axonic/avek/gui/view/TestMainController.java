package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.view.subjects.ExpSubject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nathaël N on 08/07/16.
 */
public class TestMainController extends ApplicationTest {
	static {
		System.setProperty("testfx.headless", "true");
		System.setProperty("prism.text", "t2k");
		System.setProperty("testfx.robot", "glass");
		System.setProperty("prism.order", "sw");
		System.setProperty("java.awt.headless", "true");
	}

	private Parent root;

	@Override
	public void start(Stage stage) throws IOException {
		root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/gui.fxml"));
		Scene scene = new Scene(root, 200, 200);
		stage.setScene(scene);
		stage.show();
	}

	@Test
	public void testChildren() {
		assertEquals(2, root.getChildrenUnmodifiable().size());

		assertEquals(ExpSubject.class, ((BorderPane)root).getLeft().getClass());
		assertEquals(BorderPane.class, ((BorderPane)root).getCenter().getClass());
	}
}
