package fr.axonic.avek.engine.strategy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cduffau on 26/10/16.
 */
@XmlRootElement
public class UsageDomain {
    private List<Context> contexts;

    public UsageDomain() {
        this.contexts = new ArrayList<>();
    }

    @XmlElement
    public List<Context> getContexts() {
        return contexts;
    }
}
