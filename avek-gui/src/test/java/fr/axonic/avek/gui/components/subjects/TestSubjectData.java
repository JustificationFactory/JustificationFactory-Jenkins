package fr.axonic.avek.gui.components.subjects;

import fr.axonic.avek.gui.components.MonitoredSystemView;
import fr.axonic.avek.gui.util.ConcurrentTaskManager;
import fr.axonic.avek.gui.util.UtilForTests;
import fr.axonic.avek.model.MonitoredSystem;
import fr.axonic.base.ADate;
import fr.axonic.base.ANumber;
import fr.axonic.base.AString;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;


/**
 * Created by Nathaël N on 08/07/16.
 */
public class TestSubjectData extends ApplicationTest {
	static { UtilForTests.disableGraphics(); }

	private MonitoredSystemView monitoredSystemView;

	@Override
	public void start(Stage stage) throws IOException {
		this.monitoredSystemView = new MonitoredSystemView();
		Scene scene = new Scene(monitoredSystemView, 200, 200);
		stage.setScene(scene);
		stage.show();
	}

	@Test
	public void testMonitoredSystem() throws Exception {
		ConcurrentTaskManager ctm = new ConcurrentTaskManager();
		MonitoredSystem ms1 = new MonitoredSystem(42);
		ms1.addCategory("Category 1");
		ms1.addAVar("Category 1", new AString("a string", "strval1"));
		ms1.addAVar("Category 1", new ANumber("an integer", 123456789));
		ms1.addAVar("Category 1", new ANumber("a double", 12345.6789));
		ms1.addAVar("Category 1", new ADate("a date",new GregorianCalendar()));

		ms1.addCategory("Category 2");
		ms1.addAVar("Category 2", new ANumber("an integer", 987654321));
		ms1.addAVar("Category 2", new ANumber("a double", 98765.4321));

		ctm.runNowOnPlatform(() -> monitoredSystemView.setMonitoredSystem(ms1));
		assertEquals(2, monitoredSystemView.getPanes().size());

		TitledPane tp = monitoredSystemView.getPanes().get(0);
		ScrollPane sp = (ScrollPane) tp.getContent();
		ListView<Label> vb = (ListView<Label>) sp.getContent();
		assertEquals(4, vb.getItems().size());

		tp = monitoredSystemView.getPanes().get(1);
		sp = (ScrollPane) tp.getContent();
		vb = (ListView<Label>) sp.getContent();
		assertEquals(2, vb.getItems().size());

		MonitoredSystem ms2 = new MonitoredSystem(21);
		ms2.addCategory("Category 1");
		ms2.addAVar("Category 1", new AString("a string", "strval1"));
		ms2.addAVar("Category 1", new ANumber("an integer", 123456789));
		ms2.addAVar("Category 1", new ADate("a date", new GregorianCalendar()));

		ms2.addCategory("Category 2");
		ms2.addAVar("Category 2", new ANumber("an integer", 987654321));
		ms2.addAVar("Category 2", new ANumber("a double", 12345.6789));
		ms2.addAVar("Category 2", new ANumber("a double", 98765.4321));

		ms2.addCategory("Category 3");

		ctm.runNowOnPlatform(() -> monitoredSystemView.setMonitoredSystem(ms2));
		assertEquals(3, monitoredSystemView.getPanes().size());

		tp = monitoredSystemView.getPanes().get(0);
		sp = (ScrollPane) tp.getContent();
		vb = (ListView<Label>) sp.getContent();
		assertEquals(3, vb.getItems().size());

		tp = monitoredSystemView.getPanes().get(1);
		sp = (ScrollPane) tp.getContent();
		vb = (ListView<Label>) sp.getContent();
		assertEquals(3, vb.getItems().size());

		tp = monitoredSystemView.getPanes().get(2);
		sp = (ScrollPane) tp.getContent();
		vb = (ListView<Label>) sp.getContent();
		assertEquals(0, vb.getItems().size());
	}
}
