package fr.axonic.avek.gui.components.parameters.leaves;

import com.sun.javafx.application.PlatformImpl;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanPane;
import fr.axonic.avek.gui.util.TestEnum;
import fr.axonic.avek.gui.util.UtilForTests;
import fr.axonic.base.AEnum;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.engine.AVarHelper;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nathaël N on 25/07/16.
 */
public class TestRangedParameter {
    static {
        PlatformImpl.startup(() -> {});
        UtilForTests.disableGraphics();
    }

    private ARangedEnum aRangedEnum;
	private RangedParameter rangedParameter;
    private Map<TestEnum, JellyBeanItem<TestEnum, Boolean>> jellyBeanItems;

    @SuppressWarnings("unchecked")
    @Before
	public void before() throws IOException, VerificationException, NoSuchFieldException, IllegalAccessException {
        aRangedEnum =new ARangedEnum<>(TestEnum.class);
        aRangedEnum.setLabel("Effect Type");
        aRangedEnum.setCode("effectType");
        aRangedEnum.setPath("fr.axonic");
        aRangedEnum.setRange(AVarHelper.transformToAVar(Arrays.asList(TestEnum.values())));

        aRangedEnum.setValue(TestEnum.B);

		rangedParameter = new RangedParameter(2, aRangedEnum);

        // jellyBeanPane is private > set accessible from here
        Field jellyBeanPaneField = RangedParameter.class.getDeclaredField("jellyBeanPane");
        jellyBeanPaneField.setAccessible(true);

        // Get jellyBeans...
        JellyBeanPane jellyBeanPane = (JellyBeanPane) jellyBeanPaneField.get(rangedParameter);
        jellyBeanItems = new HashMap<>();
        for(JellyBeanItem item : jellyBeanPane.getJellyBeans()) {
            jellyBeanItems.put((TestEnum) item.getIdentifier(), item);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testNoEdition() throws VerificationException {
        assertEquals(1, aRangedEnum.getRange().size());
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                    new AEnum<>(TestEnum.class, TestEnum.B)));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testWithClicks() throws VerificationException, NoSuchFieldException, IllegalAccessException {
        jellyBeanItems.get(TestEnum.A).setState(true);
        assertEquals(2, aRangedEnum.getRange().size());
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(TestEnum.class, TestEnum.A)));
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(TestEnum.class, TestEnum.B)));

        jellyBeanItems.get(TestEnum.D).setState(true);
        assertEquals(3, aRangedEnum.getRange().size());
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(TestEnum.class, TestEnum.A)));
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(TestEnum.class, TestEnum.B)));
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(TestEnum.class, TestEnum.D)));

        jellyBeanItems.get(TestEnum.A).setState(false);
        assertEquals(2, aRangedEnum.getRange().size());
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(TestEnum.class, TestEnum.B)));
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(TestEnum.class, TestEnum.D)));

        jellyBeanItems.get(TestEnum.C).setState(true);
        assertEquals(3, aRangedEnum.getRange().size());
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(TestEnum.class, TestEnum.B)));
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(TestEnum.class, TestEnum.C)));
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(TestEnum.class, TestEnum.D)));
    }
}
