package fr.axonic.avek.bus.translator;

import fr.axonic.avek.instance.clinical.conclusion.Effect;
import fr.axonic.avek.instance.clinical.conclusion.EffectStateEnum;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.gui.model.GUIEffect;
import fr.axonic.base.AEnum;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AVar;
import fr.axonic.validation.exception.VerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Nathaël N on 28/07/16.
 */
class EffectToGuiEffect extends DataTranslator<AList<Effect>, GUIEffect> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EffectToGuiEffect.class);

    //  //  //  //  //  TYPE TRANSLATORS //  //  //  //  //

    @Override
    protected GUIEffect translate(AList<Effect> effectAList) {
        final GUIEffect guiEffect = new GUIEffect<>(effectAList);

        for(Effect effect : effectAList) {
            // Store state for set it in JellyBeanItem
            EffectStateEnum state = effect.getEffectType().getValue().getState().getValue();
            AEnum<EffectStateEnum> stateAsAEnum = null;
            for(AEnum<EffectStateEnum> aEnum : effect.getEffectType().getValue().getState().getRange()) {
                if(aEnum.getValue().equals(state)) {
                    stateAsAEnum = aEnum;
                }
            }

            JellyBeanItem<Effect, EffectStateEnum, AEnum<EffectStateEnum>> jbi
                    = new JellyBeanItem<>(effect,
                    effect.getEffectType().getValue().getState().getRange());

            jbi.setGetLabelMethod(a -> a.getEffectType().getValue().getLabel());
            jbi.setGetStateLabelMethod(EffectStateEnum::getLabel);
            jbi.setGetStateFromItemListMethod(AVar::getValue);
            jbi.setSetStateMethod((theEffect, newState) -> {
                try {
                    theEffect.getEffectType().getValue().getState().setValue(newState);
                } catch (VerificationException e) {
                    LOGGER.error("Cannot set state "+newState+" to "+theEffect, e);
                }
            });
            if(stateAsAEnum != null) {
                jbi.setState(stateAsAEnum);
            }

            guiEffect.addAndBind(jbi,
                    () -> {
                        if (!effectAList.contains(effect)) {
                            effectAList.add(effect);
                        }
                    },
                    ()-> effectAList.remove(effect));
        }

        effectAList.clear();
        return guiEffect;
    }
}