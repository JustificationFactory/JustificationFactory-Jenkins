package fr.axonic.avek.gui.view.subjects;

import fr.axonic.avek.gui.Main;
import fr.axonic.avek.gui.util.ConcurrentTaskManager;
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
	static { Main.disableGraphics(); }

	private ExpSubject subject;
	private Accordion acc;

	@Override
	public void start(Stage stage) throws IOException {
		this.subject = new ExpSubject();
		Scene scene = new Scene(subject, 200, 200);
		stage.setScene(scene);
		stage.show();

		acc = (Accordion) subject.getCenter();
	}

	@Test
	public void testMonitoredSystem() throws ExecutionException, InterruptedException {
		ConcurrentTaskManager ctm = new ConcurrentTaskManager();

		MonitoredSystem ms = new MonitoredSystem(42);
		ms.addCategory("Category 1");
		ms.addAVar("Category 1", new AString("a string", "strval1"));
		ms.addAVar("Category 1", new ANumber("an integer", 123456789));
		ms.addAVar("Category 1", new ANumber("a double", 12345.6789));
		ms.addAVar("Category 1", new ADate("a date", Calendar.getInstance().getTime()));

		ms.addCategory("Category 2");
		ms.addAVar("Category 2", new ANumber("an integer", 987654321));
		ms.addAVar("Category 2", new ANumber("a double", 98765.4321));

		subject.setMonitoredSystem(ms);
		assertEquals(2, acc.getPanes().size());

		TitledPane tp = acc.getPanes().get(0);
		ScrollPane sp = (ScrollPane) tp.getContent();
		VBox vb = (VBox) sp.getContent();
		assertEquals(4, vb.getChildren().size());

		tp = acc.getPanes().get(1);
		sp = (ScrollPane) tp.getContent();
		vb = (VBox) sp.getContent();
		assertEquals(2, vb.getChildren().size());

		ms = new MonitoredSystem(21);
		ms.addCategory("Category 1");
		ms.addAVar("Category 1", new AString("a string", "strval1"));
		ms.addAVar("Category 1", new ANumber("an integer", 123456789));
		ms.addAVar("Category 1", new ADate("a date", Calendar.getInstance().getTime()));

		ms.addCategory("Category 2");
		ms.addAVar("Category 2", new ANumber("an integer", 987654321));
		ms.addAVar("Category 2", new ANumber("a double", 12345.6789));
		ms.addAVar("Category 2", new ANumber("a double", 98765.4321));

		ms.addCategory("Category 3");

		subject.setMonitoredSystem(ms);
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
