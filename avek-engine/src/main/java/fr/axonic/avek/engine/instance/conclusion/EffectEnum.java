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
    EFFICIENT("efficient","Efficient",EffectStateEnum.values()), UNDESIRABLE("undesirable","Undesirable",EffectStateEnum.values()), STRONGLY_UNDESIRABLE("stronglyUndesirable","Strongly Undesirable",EffectStateEnum.values()), UNKNOWN("unknown","Unknown",EffectStateEnum.UNKNOWN);

    private String code, path, label;
    public ARangedEnum<EffectStateEnum> state;

    EffectEnum(String code, String label, EffectStateEnum ... effectStateEnums ){
        this.code = code;
        this.path = "fr.axonic.effectType";
        this.label = label;
        state=new ARangedEnum<>();
        state.setLabel("Effect State");
        state.setPath("fr.axonic.effectType."+code);
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

    private void setCode(String code) {
        this.code = code;
    }

    private void setPath(String path) {
        this.path = path;
    }

    private void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getPath() {
        return path;
    }
}
