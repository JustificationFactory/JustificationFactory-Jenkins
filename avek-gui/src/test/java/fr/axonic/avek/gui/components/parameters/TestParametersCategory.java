package fr.axonic.avek.gui.components.parameters;

import fr.axonic.avek.gui.components.parameters.groups.GeneralizedGroup;
import fr.axonic.avek.gui.components.parameters.groups.GeneralizedRoot;
import fr.axonic.avek.gui.util.ConcurrentTaskManager;
import fr.axonic.avek.gui.util.UtilForTests;
import fr.axonic.base.ABoolean;
import fr.axonic.base.ADate;
import fr.axonic.base.ANumber;
import fr.axonic.base.AString;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nathaël N on 11/07/16.
 */
public class TestParametersCategory extends ApplicationTest {
    static {
        UtilForTests.disableGraphics();
    }

    private GeneralizedGroup pp;

    @Override
    public void start(Stage stage) throws IOException {
        this.pp = new GeneralizedRoot();

        Scene scene = new Scene(pp, 500, 300);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testAddRemoveParameters() throws Exception {
        ConcurrentTaskManager ctm = new ConcurrentTaskManager();

        assertEquals(0, pp.getChildren().size());
        ctm.runNowOnPlatform(() -> pp.addParameter(new ANumber("LabelText", 42.31)));
        assertEquals(5, pp.getChildren().size());

        ctm.runNowOnPlatform(() -> pp.addParameter(new ANumber("2ndLabelText", 12.34)));
        assertEquals(5 + 5, pp.getChildren().size());

        ctm.runNowOnPlatform(() -> pp.addParameter(new ANumber("3rdLabelText", 42)));
        assertEquals(5 + 5 + 5, pp.getChildren().size());

        ctm.runNowOnPlatform(() -> pp.rmParameter("2ndLabelText"));
        assertEquals(5 + 5/* +5-5 */, pp.getChildren().size());
    }

    @Test
    public void testDifferentParametersTypes() throws Exception {
        ConcurrentTaskManager ctm = new ConcurrentTaskManager();

        ctm.runNowOnPlatform(() -> pp.addParameter(new ANumber("LabelText", 42.31)));

        assertEquals(5, pp.getChildren().size());

        ctm.runNowOnPlatform(() -> pp.addParameter(new ABoolean("Boolbool", true)));
        assertEquals(5 + 5, pp.getChildren().size());

        ctm.runNowOnPlatform(() -> pp.addParameter(new ADate("Datedate", new GregorianCalendar())));
        assertEquals(5 + 5 + 5, pp.getChildren().size());

        ctm.runNowOnPlatform(() -> pp.addParameter(new AString("Strstr", "LaString")));
        assertEquals(5 + 5 + 5 + 4, pp.getChildren().size());
    }
}
