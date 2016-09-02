package fr.axonic.avek.bus.translator;

import fr.axonic.avek.engine.instance.conclusion.Effect;
import fr.axonic.avek.engine.instance.conclusion.EffectEnum;
import fr.axonic.avek.engine.instance.conclusion.EffectStateEnum;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.gui.model.GUIEffect;
import fr.axonic.base.AEnum;
import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
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
            JellyBeanItem<EffectEnum, AEnum<EffectStateEnum>> jbi
                    = new JellyBeanItem<>(effect.getEffectType().getValue(),
                    effect.getEffectType().getValue().getState().getRange());

            jbi.getFormat().setGetLabelMethod(EffectEnum::getLabel);
            jbi.getFormat().setGetStateLabelMethod(AEntity::getLabel);
            jbi.getFormat().setGetStateValueMethod(a -> a.getValue().toString());

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