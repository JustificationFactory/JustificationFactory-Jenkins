package fr.axonic.avek.bus.translator;

import fr.axonic.avek.engine.instance.conclusion.Effect;
import fr.axonic.avek.engine.instance.conclusion.EffectEnum;
import fr.axonic.avek.engine.instance.conclusion.EffectStateEnum;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.gui.model.GUIEffect;
import fr.axonic.base.engine.AList;
import fr.axonic.validation.exception.VerificationException;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Nathaël N on 28/07/16.
 */
class JellyBeanItemToEffect extends DataTranslator<GUIEffect, AList<Effect>> {
    private final static Logger LOGGER = Logger.getLogger(JellyBeanItemToEffect.class);

    @Override
    protected AList<Effect> translate(GUIEffect effectsAsJellyBeanItems) {
        final AList<Effect> effectList = new AList<>();

        for(JellyBeanItem jbi : effectsAsJellyBeanItems) {
            effectList.add(jellyBeanItemToEffect(jbi));
        }

        /*effectList.addAll(effectsAsJellyBeanItems
                .stream()
                .map(DataTranslator::jellyBeanItemToEffect)
                .collect(Collectors.toList()));*/

        return effectList;
    }

    private Effect jellyBeanItemToEffect(JellyBeanItem jellyBeanItem) {
        EffectEnum[] eetab = EffectEnum.values();

        for (EffectEnum effectEnum : eetab) {
            if(jellyBeanItem.getText().equals(effectEnum.getLabel())) {
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

        throw new RuntimeException("No effect corresponding to JellyBeanItem : "+jellyBeanItem.getText());
    }

}
