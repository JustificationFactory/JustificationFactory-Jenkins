package fr.axonic.avek.gui.components.parameters;

import com.sun.javafx.application.PlatformImpl;
import fr.axonic.avek.engine.instance.conclusion.EffectEnum;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanPane;
import fr.axonic.avek.gui.components.parameters.leaves.RangedParameter;
import fr.axonic.avek.gui.util.UtilForTests;
import fr.axonic.base.AEnum;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.engine.AVarHelper;
import fr.axonic.validation.exception.VerificationException;
import org.junit.Before;
import org.junit.Ignore;
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
    private Map<EffectEnum, JellyBeanItem<Boolean>> jellyBeanItems;

    @Before
	public void before() throws IOException, VerificationException, NoSuchFieldException, IllegalAccessException {
        aRangedEnum =new ARangedEnum<>(EffectEnum.class);
        aRangedEnum.setLabel("Effect Type");
        aRangedEnum.setCode("effectType");
        aRangedEnum.setPath("fr.axonic");
        aRangedEnum.setRange(AVarHelper.transformToAVar(Arrays.asList(EffectEnum.values())));

        aRangedEnum.setValue(EffectEnum.UNDESIRABLE);

		rangedParameter = new RangedParameter(2, aRangedEnum);

        // jellyBeanPane is private > set accessible from here
        Field jellyBeanPaneField = RangedParameter.class.getDeclaredField("jellyBeanPane");
        jellyBeanPaneField.setAccessible(true);

        // Get jellyBeans...
        JellyBeanPane jellyBeanPane = (JellyBeanPane) jellyBeanPaneField.get(rangedParameter);
        jellyBeanItems = new HashMap<>();
        for(JellyBeanItem item : jellyBeanPane.getJellyBeans()) {
            jellyBeanItems.put(EffectEnum.valueOf(item.getText()), item);
        }
    }

    @Ignore
    @Test
    public void testNoEdition() throws VerificationException {
        assertEquals(1, aRangedEnum.getRange().size());
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                    new AEnum<>(EffectEnum.class, EffectEnum.UNDESIRABLE)));
    }

    @Ignore
    @Test
    public void testWithClicks() throws VerificationException, NoSuchFieldException, IllegalAccessException {
        jellyBeanItems.get(EffectEnum.EFFICIENT).setState(true);
        assertEquals(2, aRangedEnum.getRange().size());
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(EffectEnum.class, EffectEnum.UNDESIRABLE)));
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(EffectEnum.class, EffectEnum.EFFICIENT)));

        assertEquals(3, aRangedEnum.getRange().size());
        jellyBeanItems.get(EffectEnum.UNKNOWN).setState(true);
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(EffectEnum.class, EffectEnum.UNDESIRABLE)));
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(EffectEnum.class, EffectEnum.EFFICIENT)));
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(EffectEnum.class, EffectEnum.UNKNOWN)));

        assertEquals(2, aRangedEnum.getRange().size());
        jellyBeanItems.get(EffectEnum.EFFICIENT).setState(false);
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(EffectEnum.class, EffectEnum.UNDESIRABLE)));
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(EffectEnum.class, EffectEnum.UNKNOWN)));

        assertEquals(3, aRangedEnum.getRange().size());
        jellyBeanItems.get(EffectEnum.STRONGLY_UNDESIRABLE).setState(true);
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(EffectEnum.class, EffectEnum.UNDESIRABLE)));
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(EffectEnum.class, EffectEnum.STRONGLY_UNDESIRABLE)));
        assertTrue(AVarHelper.containsByValue(aRangedEnum.getRange(),
                new AEnum<>(EffectEnum.class, EffectEnum.UNKNOWN)));
    }


}
