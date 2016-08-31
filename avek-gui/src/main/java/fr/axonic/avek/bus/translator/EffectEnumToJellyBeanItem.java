package fr.axonic.avek.bus.translator;

import fr.axonic.avek.engine.instance.conclusion.Effect;
import fr.axonic.avek.engine.instance.conclusion.EffectEnum;
import fr.axonic.avek.engine.instance.conclusion.EffectStateEnum;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.gui.model.GUIEffect;
import fr.axonic.base.AEnum;
import fr.axonic.base.engine.AList;
import fr.axonic.base.engine.AVar;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nathaël N on 28/07/16.
 */
class EffectEnumToJellyBeanItem extends DataTranslator<List<EffectEnum>, GUIEffect> {
    private final static Logger LOGGER = Logger.getLogger(EffectEnumToJellyBeanItem.class);

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
        List<EffectStateEnum> list =
                effectEnum.getState().getRange().getList()
                        .stream()
                        .map(AVar::getValue)
                        .collect(Collectors.toList());

        return new JellyBeanItem<>(effectEnum, list);
    }

}
