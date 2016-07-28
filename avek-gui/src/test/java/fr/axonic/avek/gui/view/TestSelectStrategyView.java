package fr.axonic.avek.gui.view;

import fr.axonic.avek.gui.util.UtilForTests;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.testfx.framework.junit.ApplicationTest;

/**
 * Created by Nathaël N on 27/07/16.
 */
public class TestSelectStrategyView extends ApplicationTest {
	static { UtilForTests.disableGraphics(); }

	private MainFrame mainFrame;

	@Override
	public void start(Stage stage) throws Exception {
		mainFrame = new MainFrame();

		stage.setScene(new Scene(mainFrame, 800, 600));
		stage.show();
	}
}
