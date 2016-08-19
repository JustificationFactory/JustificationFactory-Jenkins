package fr.axonic.avek.gui.components.jellybeans;

import fr.axonic.avek.gui.util.ConcurrentTaskManager;
import fr.axonic.avek.gui.util.ExampleState;
import fr.axonic.avek.gui.util.UtilForTests;
import fr.axonic.base.engine.AEnumItem;
import fr.axonic.validation.exception.VerificationException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nathaël N on 07/07/16.
 */
public class TestJellyBeans extends ApplicationTest {
    static {
        UtilForTests.disableGraphics();
    }

    private JellyBeanItem<ExampleState> jbi;
    private JellyBean<ExampleState> jb;
    private Button jbText, jbCross;

    @Override
    public void start(Stage stage) throws IOException {
        this.jb = new JellyBean<>();

        Scene scene = new Scene(jb, 200, 200);
        stage.setScene(scene);
        stage.show();

        jbText = (Button) (jb.getChildren().get(0));
        jbCross = (Button) (jb.getChildren().get(1));
    }

    @Before
    public void before() throws Exception {
        ConcurrentTaskManager ctm = new ConcurrentTaskManager();

        jbi = new JellyBeanItem<>(new AEnumItem() {
            @Override
            public String getLabel() {
                return "The text";
            }

            @Override public String getCode() {return null;}
            @Override public String getPath() {return null;}
        }, Arrays.asList(ExampleState.values()));
        ctm.runNowOnPlatform(() -> jb.set(jbi));
    }

    @Test
    public void testReadOnly() throws VerificationException {
        this.jbi.setState(ExampleState.VERY_LOW);
        jbi.setEditable(false);

        assertEquals(ExampleState.VERY_LOW, jbi.getState());

        clickOn(jbText);
        assertEquals(ExampleState.VERY_LOW, jbi.getState());

        clickOn(jbText); // Medium
        clickOn(jbText);
        assertEquals(ExampleState.VERY_LOW, jbi.getState());

        clickOn(jbText); // Very high
        clickOn(jbText); // Very Low
        clickOn(jbText); // low
        clickOn(jbText);
        assertEquals(ExampleState.VERY_LOW, jbi.getState());

        // try delete
        clickOn(jbCross);
        long timeout = Calendar.getInstance().getTimeInMillis() + 1_000; // 1s
        while (Calendar.getInstance().getTimeInMillis() < timeout
                && !calledDelete.contains(jb)) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
            }
        }

        assertTrue("OnDelete called after 1s", !calledDelete.contains(jb));
    }

    @Test
    public void testStateChange() throws Exception {
        ConcurrentTaskManager ctm = new ConcurrentTaskManager();

        ctm.runNowOnPlatform(() -> this.jb.setOnDelete(this::calledOnDelete));
        jbi.setEditable(true);

        assertEquals(ExampleState.VERY_LOW, jbi.getState());
        clickOn(jbText);
        assertEquals(ExampleState.LOW, jbi.getState());
        clickOn(jbText);
        assertEquals(ExampleState.MEDIUM, jbi.getState());
        clickOn(jbText);
        assertEquals(ExampleState.HIGH, jbi.getState());
        clickOn(jbText);
        assertEquals(ExampleState.VERY_HIGH, jbi.getState());
        clickOn(jbText);
        assertEquals(ExampleState.VERY_LOW, jbi.getState());
        clickOn(jbText);
        assertEquals(ExampleState.LOW, jbi.getState());
        clickOn(jbText);
        assertEquals(ExampleState.MEDIUM, jbi.getState());

        // try delete
        clickOn(jbCross);
        long timeout = Calendar.getInstance().getTimeInMillis() + 1_000; // 1s
        while (Calendar.getInstance().getTimeInMillis() < timeout
                && !calledDelete.contains(jb)) {
            sleep(100);
        }

        assertTrue("OnDelete not called after more than 1s", calledDelete.contains(jb));
    }

    private final Set<JellyBean> calledDelete = new HashSet<>();
    private void calledOnDelete(JellyBean jellyBean) {
        calledDelete.add(jellyBean);
    }
}
