package fr.axonic.avek.engine.instance.conclusion;

import fr.axonic.base.ARangedEnum;
import fr.axonic.base.engine.AEnumItem;
import fr.axonic.base.engine.AVarHelper;
import fr.axonic.validation.exception.VerificationException;

import java.util.Arrays;

/**
 * Created by cduffau on 04/08/16.
 */
public enum EffectEnum implements AEnumItem{
    EFFICIENT(EffectStateEnum.values()), UNDESIRABLE(EffectStateEnum.values()), STRONGLY_UNDESIRABLE(EffectStateEnum.values()), UNKNOWN(EffectStateEnum.UNKNOWN);


    public ARangedEnum<EffectStateEnum> state;

    EffectEnum(EffectStateEnum ... effectStateEnums ){
        state=new ARangedEnum<>();
        state.setLabel("Effect State");
        state.setPath("fr.axonic.effectType");
        state.setCode("state");
        try {
            state.setRange(AVarHelper.transformToAVar(Arrays.asList(effectStateEnums)));
        } catch (VerificationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public ARangedEnum<EffectStateEnum> getState() {
        return state;
    }

    private void setState(ARangedEnum<EffectStateEnum> state) {
        this.state = state;
    }
    public void setStateValue(EffectStateEnum state) throws VerificationException {
        this.state.setValue(state);
    }

    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getPath() {
        return null;
    }
}
