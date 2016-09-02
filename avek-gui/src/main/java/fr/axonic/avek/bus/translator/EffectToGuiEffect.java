package fr.axonic.avek.bus.translator;

import fr.axonic.avek.engine.instance.conclusion.Effect;
import fr.axonic.avek.engine.instance.conclusion.EffectEnum;
import fr.axonic.avek.engine.instance.conclusion.EffectStateEnum;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.gui.model.GUIEffect;
import fr.axonic.base.AEnum;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.validation.exception.VerificationException;
import org.apache.log4j.Logger;

/**
 * Created by Nathaël N on 28/07/16.
 */
class EffectToGuiEffect extends DataTranslator<AList<Effect>, GUIEffect> {
    private final static Logger LOGGER = Logger.getLogger(EffectToGuiEffect.class);

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

            JellyBeanItem<EffectEnum, AEnum<EffectStateEnum>> jbi
                    = new JellyBeanItem<>(effect.getEffectType().getValue(),
                    effect.getEffectType().getValue().getState().getRange());

            jbi.getFormat().setGetLabelMethod(EffectEnum::getLabel);
            jbi.getFormat().setGetStateLabelMethod(AEntity::getLabel);
            jbi.getFormat().setGetStateValueMethod(a -> a.getValue().toString());
            jbi.addStateChangeListener((lastState, newState) -> {
                try {
                    effect.getEffectType().getValue().setStateValue(newState.getObject().getValue());
                } catch (VerificationException e) {
                    LOGGER.error("Impossible to change state of effectEnum", e);
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