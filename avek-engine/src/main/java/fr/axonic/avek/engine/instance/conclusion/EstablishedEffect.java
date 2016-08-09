package fr.axonic.avek.engine.instance.conclusion;

import fr.axonic.avek.engine.evidence.Element;
import fr.axonic.base.engine.AList;

import java.util.List;

/**
 * Created by cduffau on 09/08/16.
 */
public class EstablishedEffect extends Element{
    private Experimentation experimentation;
    private AList<Effect> effects;

    public EstablishedEffect(Experimentation experimentation, AList<Effect> effects) {
        this.experimentation = experimentation;
        this.effects = effects;
    }

    public Experimentation getExperimentation() {
        return experimentation;
    }

    public void setExperimentation(Experimentation experimentation) {
        this.experimentation = experimentation;
    }

    public AList<Effect> getEffects() {
        return effects;
    }

    public void setEffects(AList<Effect> effects) {
        this.effects = effects;
    }

    @Override
    public String toString() {
        return "EstablishedEffect{" +
                "experimentation=" + experimentation +
                ", effects=" + effects +
                '}';
    }
}
