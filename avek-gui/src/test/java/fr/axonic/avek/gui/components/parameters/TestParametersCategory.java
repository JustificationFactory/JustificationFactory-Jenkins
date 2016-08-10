package fr.axonic.avek.gui.components.parameters;

import fr.axonic.avek.gui.components.parameters.groups.GeneralizedGroup;
import fr.axonic.avek.gui.components.parameters.groups.GeneralizedRoot;
import fr.axonic.avek.gui.util.ConcurrentTaskManager;
import fr.axonic.avek.gui.util.UtilForTests;
import fr.axonic.base.ABoolean;
import fr.axonic.base.ADate;
import fr.axonic.base.ANumber;
import fr.axonic.base.AString;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
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

    private GeneralizedRoot pp;

    @Override
    public void start(Stage stage) throws IOException {
        this.pp = new GeneralizedRoot();

        Scene scene = new Scene(pp, 500, 300);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testDifferentParametersTypes() throws Exception {
        ConcurrentTaskManager ctm = new ConcurrentTaskManager();

        AList<AEntity> list = new AList<>();
        list.setLabel("Root");

        list.add(new ANumber("LabelText", 42.31));
        ctm.runNowOnPlatform(() -> pp.setAList(list));

        assertEquals(4, pp.getChildren().size());

        list.add(new ABoolean("Boolbool", true));
        ctm.runNowOnPlatform(() -> pp.setAList(list));
        assertEquals(4 + 4, pp.getChildren().size());

        list.add(new ADate("Datedate", new GregorianCalendar()));
        ctm.runNowOnPlatform(() -> pp.setAList(list));
        assertEquals(4 + 4 + 4, pp.getChildren().size());

        list.add(new AString("Strstr", "LaString"));
        ctm.runNowOnPlatform(() -> pp.setAList(list));
        assertEquals(4 + 4 + 4 + 4, pp.getChildren().size());
    }
}
