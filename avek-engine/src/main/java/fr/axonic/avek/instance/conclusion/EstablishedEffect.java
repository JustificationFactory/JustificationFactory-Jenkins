package fr.axonic.avek.instance.conclusion;

import fr.axonic.avek.engine.support.evidence.Element;
import fr.axonic.base.engine.AList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by cduffau on 09/08/16.
 */
@XmlRootElement
@XmlSeeAlso({Experimentation.class,Effect.class})
public class EstablishedEffect extends Element{
    private Experimentation experimentation;
    private AList<Effect> effects;

    private EstablishedEffect() {
    }

    public EstablishedEffect(Experimentation experimentation, AList<Effect> effects) {
        this.experimentation = experimentation;
        this.effects = effects;
    }

    @XmlElement
    public Experimentation getExperimentation() {
        return experimentation;
    }

    public void setExperimentation(Experimentation experimentation) {
        this.experimentation = experimentation;
    }

    @XmlElement
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
