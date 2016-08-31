package fr.axonic.avek.gui.components.jellybeans;

import fr.axonic.avek.gui.model.GUIEffect;
import fr.axonic.avek.gui.util.ExampleState;
import fr.axonic.avek.gui.util.UtilForTests;
import fr.axonic.base.AEnum;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.engine.AEnumItem;
import fr.axonic.base.engine.AVarHelper;
import fr.axonic.validation.exception.VerificationException;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nathaël N on 04/07/16.
 */
public class TestJellyBeanSelector extends ApplicationTest {
    static {
        UtilForTests.disableGraphics();
    }

    private JellyBeanPane jellyBeanPane;


    @Override
    public void start(Stage stage) throws VerificationException, IOException {
        JellyBeanSelector jbs = new JellyBeanSelector();
        Scene scene = new Scene(jbs, 200, 200);
        stage.setScene(scene);
        stage.show();

        GUIEffect effect = new GUIEffect();
        for (int i = 1; i <= 30; i++) {
            ExampleState val = ExampleState.values()[0];
            ARangedEnum<ExampleState> aEnum = new ARangedEnum<>(ExampleState.class, val);
            aEnum.setDefaultValue(ExampleState.MEDIUM);
            aEnum.setRange(AVarHelper.transformToAVar(Arrays.asList(ExampleState.values())));

            List<String> ls = aEnum.getRange().stream().map(AEnum::toString).collect(Collectors.toList());

            final String label = "AE" + i;
            effect.add(new JellyBeanItem<>(new AEnumItem() {
                @Override
                public String getLabel() {
                    return label;
                }

                @Override public String getCode() {return null;}
                @Override public String getPath() {return null;}
            }, ls));
        }

        // Fill experiment sample list
        jbs.setJellyBeansChoice(effect);
        jellyBeanPane = jbs.getJellyBeanPane();
    }

    @Test
    public void testSelectItem() {
        assertEquals(0, jellyBeanPane.getChildren().size());

        // Try to add 'null'
        clickOn("#comboBoxJellyBean")
                .push(KeyCode.ENTER);
        assertEquals(0, jellyBeanPane.getChildren().size());

        // Move to 'Effect 5'
        clickOn("#comboBoxJellyBean")
                .push(KeyCode.DOWN)
                .push(KeyCode.DOWN)
                .push(KeyCode.DOWN)
                .push(KeyCode.DOWN)
                .push(KeyCode.DOWN)
                .push(KeyCode.ENTER);

        // Add 'Effect 5'
        assertEquals(1, jellyBeanPane.getChildren().size());
        verifyGoodJellyBean(jellyBeanPane, 0, "AE5");

        // Add 'Effect 5' (should do nothing)
        assertEquals(1, jellyBeanPane.getChildren().size());
        verifyGoodJellyBean(jellyBeanPane, 0, "AE5");


        // Move to 'Effect 7'
        clickOn("#comboBoxJellyBean")
                .push(KeyCode.DOWN)
                .push(KeyCode.DOWN)
                .push(KeyCode.ENTER);

        // Add 'Effect 7'
        assertEquals(2, jellyBeanPane.getChildren().size());
        verifyGoodJellyBean(jellyBeanPane, 0, "AE5");
        verifyGoodJellyBean(jellyBeanPane, 1, "AE7");

        // Move to 'Effect 5'
        clickOn("#comboBoxJellyBean")
                .push(KeyCode.UP)
                .push(KeyCode.UP)
                .push(KeyCode.ENTER);

        // Add 'Effect 5' (should do nothing)
        assertEquals(2, jellyBeanPane.getChildren().size());
        verifyGoodJellyBean(jellyBeanPane, 0, "AE5");
        verifyGoodJellyBean(jellyBeanPane, 1, "AE7");

        // Move to 'Effect 3'
        clickOn("#comboBoxJellyBean")
                .push(KeyCode.UP)
                .push(KeyCode.UP)
                .push(KeyCode.ENTER);

        // Add 'Effect 3'
        assertEquals(3, jellyBeanPane.getChildren().size());
        verifyGoodJellyBean(jellyBeanPane, 0, "AE5");
        verifyGoodJellyBean(jellyBeanPane, 1, "AE7");
        verifyGoodJellyBean(jellyBeanPane, 2, "AE3");
    }

    @Test
    public void testRemoveBean() {
        // add 'Effect 5'
        clickOn("#comboBoxJellyBean")
                .push(KeyCode.DOWN)
                .push(KeyCode.DOWN)
                .push(KeyCode.DOWN)
                .push(KeyCode.DOWN)
                .push(KeyCode.DOWN)
                .push(KeyCode.ENTER);

        // Add 'Effect 7'
        clickOn("#comboBoxJellyBean")
                .push(KeyCode.DOWN)
                .push(KeyCode.DOWN)
                .push(KeyCode.ENTER);

        // Add 'Effect 3'
        clickOn("#comboBoxJellyBean")
                .push(KeyCode.UP)
                .push(KeyCode.UP)
                .push(KeyCode.UP)
                .push(KeyCode.UP)
                .push(KeyCode.ENTER);
        assertEquals(3, jellyBeanPane.getChildren().size());
        verifyGoodJellyBean(jellyBeanPane, 0, "AE5");
        verifyGoodJellyBean(jellyBeanPane, 1, "AE7");
        verifyGoodJellyBean(jellyBeanPane, 2, "AE3");

        // Remove 'Effect 7'
        JellyBean jb = (JellyBean) jellyBeanPane.getChildren().get(1);

        clickOn(jb.getChildren().get(1));

        assertEquals(2, jellyBeanPane.getChildren().size());
        verifyGoodJellyBean(jellyBeanPane, 0, "AE5");
        verifyGoodJellyBean(jellyBeanPane, 1, "AE3");

        // Re-add 'Effect 7'
        clickOn("#comboBoxJellyBean")
                .push(KeyCode.DOWN)
                .push(KeyCode.DOWN)
                .push(KeyCode.DOWN)
                .push(KeyCode.DOWN)
                .push(KeyCode.ENTER);

        assertEquals(3, jellyBeanPane.getChildren().size());
        verifyGoodJellyBean(jellyBeanPane, 0, "AE5");
        verifyGoodJellyBean(jellyBeanPane, 1, "AE3");
        verifyGoodJellyBean(jellyBeanPane, 2, "AE7");
    }

    private void verifyGoodJellyBean(Pane jellyBeanPane, int i, String name) {
        assertTrue(jellyBeanPane.getChildren().get(i) instanceof JellyBean);
        JellyBean jb = (JellyBean) jellyBeanPane.getChildren().get(i);
        assertEquals(name, jb.getItem().getText());
    }
}
