package fr.axonic.avek.bus.translator;

import fr.axonic.avek.engine.instance.conclusion.EffectEnum;
import fr.axonic.avek.engine.instance.conclusion.EffectStateEnum;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.gui.model.GUIEffect;
import fr.axonic.base.AEnum;
import fr.axonic.base.engine.AVar;
import fr.axonic.validation.exception.VerificationException;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Nathaël N on 28/07/16.
 */
class EffectEnumToGuiEffect extends DataTranslator<List<EffectEnum>, GUIEffect> {
    private static final Logger LOGGER = Logger.getLogger(EffectEnumToGuiEffect.class);

    //  //  //  //  //  TYPE TRANSLATORS //  //  //  //  //

    @Override
    protected GUIEffect translate(List<EffectEnum> effectAList) {
        final GUIEffect<?> guiEffect = new GUIEffect<>(effectAList);

        for(EffectEnum effectEnum : effectAList) {
            // Store state for set it in JellyBeanItem
            EffectStateEnum state = effectEnum.getState().getValue();
            AEnum<EffectStateEnum> stateAsAEnum = null;
            for(AEnum<EffectStateEnum> aEnum : effectEnum.getState().getRange()) {
                if(aEnum.getValue().equals(state)) {
                    stateAsAEnum = aEnum;
                }
            }


            JellyBeanItem<EffectEnum, EffectStateEnum, AEnum<EffectStateEnum>> jbi
                    = new JellyBeanItem<>(effectEnum, effectEnum.getState().getRange());

            jbi.setGetLabelMethod(EffectEnum::getLabel);
            jbi.setGetStateLabelMethod(EffectStateEnum::getLabel);
            jbi.setGetStateFromItemListMethod(AVar::getValue);
            jbi.setSetStateMethod((effect, newState) -> {
                try {
                    effect.getState().setValue(newState);
                } catch (VerificationException e) {
                    LOGGER.error("Cannot set state "+newState+" to "+effect, e);
                }
            });
            if(stateAsAEnum != null) {
                jbi.setState(stateAsAEnum);
            }

            guiEffect.addAndBind(jbi,
                    () -> {
                        if(!effectAList.contains(effectEnum)) {
                            effectAList.add(effectEnum);
                        }
                    },
                    () -> effectAList.remove(effectEnum));
        }

        effectAList.clear();
        return guiEffect;
    }

}
