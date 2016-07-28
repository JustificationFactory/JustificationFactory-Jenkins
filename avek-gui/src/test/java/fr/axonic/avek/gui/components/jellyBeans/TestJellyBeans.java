package fr.axonic.avek.gui.components.jellyBeans;

import fr.axonic.avek.gui.model.sample.ExampleState;
import fr.axonic.avek.gui.util.UtilForTests;
import fr.axonic.avek.model.base.ARangedEnum;
import fr.axonic.avek.model.verification.exception.VerificationException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nathaël N on 07/07/16.
 */
public class TestJellyBeans extends ApplicationTest {
	static { UtilForTests.disableGraphics(); }

	private JellyBean jb;
	private Button jbText, jbCross;

	@Override
	public void start(Stage stage) throws IOException {
		this.jb = new JellyBean();

		Scene scene = new Scene(jb, 200, 200);
		stage.setScene(scene);
		stage.show();

		jbText = (Button) (jb.getChildren().get(0));
		jbCross = (Button) (jb.getChildren().get(1));

		this.jb.setText("The Text");
	}

	@Test
	public void testReadOnly() throws VerificationException {
		ARangedEnum<ExampleState> are = new ARangedEnum<>(ExampleState.VERY_LOW);
		List<ExampleState> l = new ArrayList<>();
		Collections.addAll(l, ExampleState.values());
		are.setRange(l);

		this.jb.setStateType(are);
		// ReadOnly if not 'setOnDelete'
		//this.jb.setOnDelete(this::calledOnDelete);

		assertEquals(ExampleState.VERY_LOW, jb.getState());

		clickOn(jbText);
		assertEquals(ExampleState.VERY_LOW, jb.getState());

		clickOn(jbText); // Medium
		clickOn(jbText);
		assertEquals(ExampleState.VERY_LOW, jb.getState());

		clickOn(jbText); // Very high
		clickOn(jbText); // Very Low
		clickOn(jbText); // low
		clickOn(jbText);
		assertEquals(ExampleState.VERY_LOW, jb.getState());

		// try delete
		clickOn(jbCross);
		long timeout = Calendar.getInstance().getTimeInMillis()+1_000; // 1s
		while(Calendar.getInstance().getTimeInMillis() < timeout
				&& !calledDelete.contains(jb))
			try{ Thread.sleep(1); } catch(InterruptedException ignored){}

		assertTrue("OnDelete called after 1s", !calledDelete.contains(jb));
	}

	@Test
	public void testStateChange() throws VerificationException {
		ARangedEnum<ExampleState> are = new ARangedEnum<>(ExampleState.VERY_LOW);
		List<ExampleState> l = new ArrayList<>();
		Collections.addAll(l, ExampleState.values());
		are.setRange(l);

		this.jb.setStateType(are);
		this.jb.setOnDelete(this::calledOnDelete);

		assertEquals(ExampleState.VERY_LOW, jb.getState());

		clickOn(jbText);
		assertEquals(ExampleState.LOW, jb.getState());


		clickOn(jbText); // Medium
		clickOn(jbText);
		assertEquals(ExampleState.HIGH, jb.getState());

		clickOn(jbText); // Very high
		clickOn(jbText); // Very Low
		clickOn(jbText); // low
		clickOn(jbText);
		assertEquals(ExampleState.MEDIUM, jb.getState());

		// try delete
		clickOn(jbCross);
		long timeout = Calendar.getInstance().getTimeInMillis()+1_000; // 1s
		while(Calendar.getInstance().getTimeInMillis() < timeout
				&& !calledDelete.contains(jb))
			try{ Thread.sleep(1); } catch(InterruptedException ignored){}

		assertTrue("OnDelete not called after 1s", calledDelete.contains(jb));
	}

	private final Set<JellyBean> calledDelete = new HashSet<>();
	private void calledOnDelete(JellyBean jellyBean) {
		calledDelete.add(jellyBean);
	}
}
