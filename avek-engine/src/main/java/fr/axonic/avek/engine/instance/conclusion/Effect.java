package fr.axonic.avek.engine.instance.conclusion;

import fr.axonic.avek.engine.evidence.Element;
import fr.axonic.base.ARangedEnum;
import fr.axonic.base.engine.AVarHelper;
import fr.axonic.validation.exception.VerificationException;

import java.util.Arrays;

public class Effect extends Element {

    private ARangedEnum<EffectEnum> effectType;

    public Effect() throws VerificationException {
        super();
        effectType =new ARangedEnum<>();
        effectType.setLabel("Effect Type");
        effectType.setCode("effectType");
        effectType.setPath("fr.axonic");
        effectType.setRange(AVarHelper.transformToAVar(Arrays.asList(EffectEnum.values())));
        super.init();
    }

    public ARangedEnum<EffectEnum> getEffectType() {
        return effectType;
    }

    private void setEffectType(ARangedEnum<EffectEnum> effectType) {
        this.effectType = effectType;
    }

    public void setEffectValue(EffectEnum effect) throws VerificationException {
        this.effectType.setValue(effect);
    }

    @Override
    public String toString() {
        return "Effect{" +
                "effectType=" + effectType +
                '}';
    }
}
