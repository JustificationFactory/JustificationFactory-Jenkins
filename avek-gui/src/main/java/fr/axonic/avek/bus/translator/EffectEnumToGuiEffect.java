package fr.axonic.avek.bus.translator;

import fr.axonic.avek.engine.instance.conclusion.EffectEnum;
import fr.axonic.avek.engine.instance.conclusion.EffectStateEnum;
import fr.axonic.avek.gui.components.jellybeans.JellyBeanItem;
import fr.axonic.avek.gui.model.GUIEffect;
import fr.axonic.base.AEnum;
import org.apache.log4j.Logger;

import java.util.ArrayList;
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
        List<EffectStateEnum> list = new ArrayList<>();

        for(AEnum<EffectStateEnum> s : effectEnum.getState().getRange().getList()) {
            list.add(s.getValue());
        }

        return new JellyBeanItem<>(effectEnum, list);
    }

}
