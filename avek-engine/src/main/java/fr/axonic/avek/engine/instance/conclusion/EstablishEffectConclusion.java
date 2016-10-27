package fr.axonic.avek.engine.instance.conclusion;

import fr.axonic.avek.engine.conclusion.Conclusion;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by cduffau on 09/08/16.
 */
@XmlRootElement
@XmlSeeAlso(EstablishedEffect.class)
public class EstablishEffectConclusion extends Conclusion<EstablishedEffect>{
    private EstablishEffectConclusion() {
    }

    public EstablishEffectConclusion(String name, EstablishedEffect element) {
        super(name, element);
    }
}
