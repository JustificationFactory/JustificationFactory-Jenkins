package fr.axonic.avek.bus.translator;

import fr.axonic.avek.engine.instance.conclusion.EffectEnum;
import fr.axonic.avek.engine.instance.conclusion.EffectStateEnum;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.gui.model.GUIEffect;
import fr.axonic.base.AEnum;
import fr.axonic.base.engine.AEntity;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Nathaël N on 28/07/16.
 */
class EffectEnumToGuiEffect extends DataTranslator<List<EffectEnum>, GUIEffect> {
    private final static Logger LOGGER = Logger.getLogger(EffectEnumToGuiEffect.class);

    //  //  //  //  //  TYPE TRANSLATORS //  //  //  //  //

    @Override
    protected GUIEffect translate(List<EffectEnum> effectAList) {
        final GUIEffect<?> guiEffect = new GUIEffect<>(effectAList);

        for(EffectEnum effectEnum : effectAList) {
            JellyBeanItem<EffectEnum, AEnum<EffectStateEnum>> jbi
                    = new JellyBeanItem<>(effectEnum, effectEnum.getState().getRange());

            jbi.getFormat().setGetLabelMethod(EffectEnum::getLabel);
            jbi.getFormat().setGetStateLabelMethod(AEntity::getLabel);
            jbi.getFormat().setGetStateValueMethod(a -> a.getValue().toString());

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
