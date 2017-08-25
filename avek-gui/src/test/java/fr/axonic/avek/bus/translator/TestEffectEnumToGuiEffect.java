package fr.axonic.avek.bus.translator;

import fr.axonic.avek.instance.avek.conclusion.EffectEnum;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.gui.model.GUIEffect;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nathael on 09/09/16.
 */
public class TestEffectEnumToGuiEffect {


    @Test
    public void test() {
        List<EffectEnum> list = new ArrayList<>(Arrays.asList(EffectEnum.values()));
        Object object = DataTranslator.translateForGUI(list);
        Assert.assertTrue(object instanceof GUIEffect);

        GUIEffect guiEffect = (GUIEffect) object;

        Assert.assertEquals(EffectEnum.values().length, guiEffect.getJellyBeanItemList().size());

        for(Object item : guiEffect.getJellyBeanItemList()) {
            JellyBeanItem jbItem = (JellyBeanItem) item;

            Object linkedObject = jbItem.getLinkedObject();
            Assert.assertTrue(linkedObject instanceof EffectEnum);
            EffectEnum selectedEffect = (EffectEnum) linkedObject;

            Assert.assertEquals(0, guiEffect.getAddedItems().size());
            Assert.assertFalse(list.contains(selectedEffect));

            guiEffect.add(jbItem);
            Assert.assertEquals(1, guiEffect.getAddedItems().size());
            Assert.assertTrue(list.contains(selectedEffect));

            guiEffect.remove(jbItem);
            Assert.assertEquals(0, guiEffect.getAddedItems().size());
            Assert.assertFalse(list.contains(selectedEffect));
        }

    }


}
