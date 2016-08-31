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
class EffectToJellyBeanItem extends DataTranslator<AList<Effect>, GUIEffect> {
    private final static Logger LOGGER = Logger.getLogger(EffectToJellyBeanItem.class);

    //  //  //  //  //  TYPE TRANSLATORS //  //  //  //  //

    @Override
    protected GUIEffect translate(AList<Effect> effectAsAList) {
        final GUIEffect effectList = new GUIEffect();

        effectList.addAll(effectAsAList
                .stream()
                .map(this::effectToJellyBeanItem)
                .collect(Collectors.toList()));

        return effectList;
    }

    private JellyBeanItem effectToJellyBeanItem(Effect effectEnum) {
        LOGGER.debug("Converting effect to jellyBeanItem: "+effectEnum);

        List<EffectStateEnum> list = new ArrayList<>();
        for(AEnum ae : effectEnum.getEffectType().getValue().getState().getRange()) {
            list.add((EffectStateEnum) ae.getValue());
        }

        return new JellyBeanItem<>(effectEnum.getEffectType().getValue(), list);
    }
}
