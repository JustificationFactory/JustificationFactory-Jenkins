package fr.axonic.avek.instance.clinical.conclusion;

import fr.axonic.avek.engine.support.conclusion.Conclusion;

/**
 * Created by cduffau on 09/08/16.
 */
public class GeneralizationConclusion extends Conclusion<EstablishedEffect>{

    public GeneralizationConclusion(){
        super();
    }

    public GeneralizationConclusion(String name, EstablishedEffect element) {
        super(name, element);
    }
}
