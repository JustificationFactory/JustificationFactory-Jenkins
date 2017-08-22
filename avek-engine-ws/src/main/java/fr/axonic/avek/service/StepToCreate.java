package fr.axonic.avek.service;

import fr.axonic.avek.engine.support.SupportRole;
import fr.axonic.avek.engine.support.conclusion.Conclusion;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by cduffau on 22/08/17.
 */
@XmlRootElement
public class StepToCreate {

    private List<SupportRole> supports;
    private Conclusion conclusion;

    public StepToCreate() {
    }

    public StepToCreate(List<SupportRole> supports, Conclusion conclusion) {
        this.supports = supports;
        this.conclusion = conclusion;
    }

    public List<SupportRole> getSupports() {
        return supports;
    }

    public void setSupports(List<SupportRole> supports) {
        this.supports = supports;
    }

    public Conclusion getConclusion() {
        return conclusion;
    }

    public void setConclusion(Conclusion conclusion) {
        this.conclusion = conclusion;
    }
}
