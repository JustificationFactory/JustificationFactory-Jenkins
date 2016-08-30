package fr.axonic.avek.bus;

import fr.axonic.avek.engine.instance.conclusion.Effect;
import fr.axonic.avek.engine.instance.conclusion.EffectEnum;
import fr.axonic.avek.engine.instance.conclusion.EffectStateEnum;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.base.engine.AList;
import fr.axonic.validation.exception.VerificationException;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Nathaël N on 28/07/16.
 */
public class DataTranslator {
    private final static Logger LOGGER = Logger.getLogger(DataTranslator.class);


    //  //  //  //  //  TYPE TRANSLATORS //  //  //  //  //

    public static AList<Effect> jellyBeanItemsToEffectList(List<JellyBeanItem> effectsAsJellyBeanItems) {
        final AList<Effect> effectList = new AList<>();

        effectList.addAll(effectsAsJellyBeanItems
                .stream()
                .map(DataTranslator::jellyBeanItemToEffect)
                .collect(Collectors.toList()));

        return effectList;
    }
    private static Effect jellyBeanItemToEffect(JellyBeanItem jellyBeanItem) {
        EffectEnum[] eetab = EffectEnum.values();

        for (EffectEnum effectEnum : eetab) {
            if(jellyBeanItem.getText().equals(effectEnum.toString())) {
                try {
                    Effect effect = new Effect();
                    effectEnum.setStateValue((EffectStateEnum) jellyBeanItem.getState());
                    effect.setEffectValue(effectEnum);
                    return effect;
                } catch (VerificationException e) {
                    LOGGER.error("Impossible to add effect "+jellyBeanItem, e);
                }
            }
        }

        return null;
    }
}
