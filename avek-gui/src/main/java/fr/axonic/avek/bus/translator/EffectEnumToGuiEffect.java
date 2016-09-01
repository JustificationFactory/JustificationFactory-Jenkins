package fr.axonic.avek.bus.translator;

import com.sun.org.apache.bcel.internal.generic.LOOKUPSWITCH;
import fr.axonic.avek.engine.instance.conclusion.EffectEnum;
import fr.axonic.avek.engine.instance.conclusion.EffectStateEnum;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.gui.model.GUIEffect;
import fr.axonic.base.AEnum;
import fr.axonic.base.engine.AEntity;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nathaël N on 28/07/16.
 */
class EffectEnumToGuiEffect extends DataTranslator<List<EffectEnum>, GUIEffect> {
    private final static Logger LOGGER = Logger.getLogger(EffectEnumToGuiEffect.class);

    //  //  //  //  //  TYPE TRANSLATORS //  //  //  //  //

    @Override
    protected GUIEffect translate(List<EffectEnum> effectAsAList) {
        final GUIEffect effectList = new GUIEffect();

        effectList.addAll(effectAsAList
                .stream()
                .map(this::effectToJellyBeanItem)
                .collect(Collectors.toList()));

        return effectList;
    }

    private JellyBeanItem effectToJellyBeanItem(EffectEnum effectEnum) {
        JellyBeanItem<EffectEnum, AEnum<EffectStateEnum>> jbi
                = new JellyBeanItem<>(effectEnum, effectEnum.getState().getRange());

        jbi.getFormat().setGetLabelMethod(EffectEnum::getLabel);
        jbi.getFormat().setGetStateLabelMethod(AEntity::getLabel);
        jbi.getFormat().setGetStateValueMethod(a -> a.getValue().toString());

        return jbi;
    }

}
