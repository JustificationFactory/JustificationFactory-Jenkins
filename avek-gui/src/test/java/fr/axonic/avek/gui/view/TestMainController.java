package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.components.subjects.Subject;
import fr.axonic.avek.gui.util.Util;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nathaël N on 08/07/16.
 */
public class TestMainController extends ApplicationTest {
	static { Util.disableGraphics(); }

	private Parent root;

	@Override
	public void start(Stage stage) throws IOException {
		root = new GeneralizedView();
		Scene scene = new Scene(root, 200, 200);
		stage.setScene(scene);
		stage.show();
	}

	@Test
	public void testChildren() {
		assertEquals(2, root.getChildrenUnmodifiable().size());

		assertEquals(Subject.class, ((BorderPane) root).getLeft().getClass());
		assertEquals(BorderPane.class, ((BorderPane) root).getCenter().getClass());
	}
}
