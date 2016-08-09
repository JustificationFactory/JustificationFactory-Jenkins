package fr.axonic.avek.engine.instance.conclusion;

import fr.axonic.base.ARangedEnum;
import fr.axonic.base.engine.AVarHelper;
import fr.axonic.validation.exception.VerificationException;

import java.util.Arrays;

/**
 * Created by cduffau on 04/08/16.
 */
public enum EffectEnum {
    EFFICIENT(EffectStateEnum.values()), UNDESIRABLE(EffectStateEnum.values()), STRONGLY_UNDESIRABLE(EffectStateEnum.values()), UNKNOWN(EffectStateEnum.UNKNOWN);


    public ARangedEnum<EffectStateEnum> state;

    EffectEnum(EffectStateEnum ... effectStateEnums ) throws VerificationException {
        state=new ARangedEnum<>();
        state.setLabel("Effect State");
        state.setPath("fr.axonic.effectType");
        state.setCode("state");
        state.setRange(AVarHelper.transformToAVar(Arrays.asList(effectStateEnums)));
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
}
