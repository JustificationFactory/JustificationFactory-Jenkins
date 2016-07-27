package fr.axonic.avek.gui.components.subjects;

import fr.axonic.avek.gui.components.MonitoredSystemPane;
import fr.axonic.avek.gui.util.ConcurrentTaskManager;
import fr.axonic.avek.gui.util.UtilForTests;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.avek.model.base.ADate;
import fr.axonic.avek.model.base.ANumber;
import fr.axonic.avek.model.base.AString;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;


/**
 * Created by Nathaël N on 08/07/16.
 */
public class TestSubjectData extends ApplicationTest {
	static { UtilForTests.disableGraphics(); }

	private MonitoredSystemPane monitoredSystemPane;
	private Accordion acc;

	@Override
	public void start(Stage stage) throws IOException {
		this.monitoredSystemPane = new MonitoredSystemPane();
		Scene scene = new Scene(monitoredSystemPane, 200, 200);
		stage.setScene(scene);
		stage.show();

		acc = (Accordion) monitoredSystemPane.getCenter();
	}

	@Test
	public void testMonitoredSystem() throws ExecutionException, InterruptedException {
		ConcurrentTaskManager ctm = new ConcurrentTaskManager();
		MonitoredSystem ms1 = new MonitoredSystem(42);
		ms1.addCategory("Category 1");
		ms1.addAVar("Category 1", new AString("a string", "strval1"));
		ms1.addAVar("Category 1", new ANumber("an integer", 123456789));
		ms1.addAVar("Category 1", new ANumber("a double", 12345.6789));
		ms1.addAVar("Category 1", new ADate("a date", Calendar.getInstance().getTime()));

		ms1.addCategory("Category 2");
		ms1.addAVar("Category 2", new ANumber("an integer", 987654321));
		ms1.addAVar("Category 2", new ANumber("a double", 98765.4321));

		ctm.runNowOnPlatform(() -> monitoredSystemPane.setMonitoredSystem(ms1));
		assertEquals(2, acc.getPanes().size());

		TitledPane tp = acc.getPanes().get(0);
		ScrollPane sp = (ScrollPane) tp.getContent();
		VBox vb = (VBox) sp.getContent();
		assertEquals(4, vb.getChildren().size());

		tp = acc.getPanes().get(1);
		sp = (ScrollPane) tp.getContent();
		vb = (VBox) sp.getContent();
		assertEquals(2, vb.getChildren().size());

		MonitoredSystem ms2 = new MonitoredSystem(21);
		ms2.addCategory("Category 1");
		ms2.addAVar("Category 1", new AString("a string", "strval1"));
		ms2.addAVar("Category 1", new ANumber("an integer", 123456789));
		ms2.addAVar("Category 1", new ADate("a date", Calendar.getInstance().getTime()));

		ms2.addCategory("Category 2");
		ms2.addAVar("Category 2", new ANumber("an integer", 987654321));
		ms2.addAVar("Category 2", new ANumber("a double", 12345.6789));
		ms2.addAVar("Category 2", new ANumber("a double", 98765.4321));

		ms2.addCategory("Category 3");

		ctm.runNowOnPlatform(() -> monitoredSystemPane.setMonitoredSystem(ms2));
		assertEquals(3, acc.getPanes().size());

		tp = acc.getPanes().get(0);
		sp = (ScrollPane) tp.getContent();
		vb = (VBox) sp.getContent();
		assertEquals(3, vb.getChildren().size());

		tp = acc.getPanes().get(1);
		sp = (ScrollPane) tp.getContent();
		vb = (VBox) sp.getContent();
		assertEquals(3, vb.getChildren().size());

		tp = acc.getPanes().get(2);
		sp = (ScrollPane) tp.getContent();
		vb = (VBox) sp.getContent();
		assertEquals(0, vb.getChildren().size());
	}
}
