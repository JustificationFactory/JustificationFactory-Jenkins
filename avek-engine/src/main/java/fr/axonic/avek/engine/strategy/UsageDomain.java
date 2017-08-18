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

    @Override
    public String toString() {
        return "UsageDomain{" +
                "contexts=" + contexts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsageDomain)) return false;

        UsageDomain that = (UsageDomain) o;

        return contexts != null ? contexts.equals(that.contexts) : that.contexts == null;
    }

    @Override
    public int hashCode() {
        return contexts != null ? contexts.hashCode() : 0;
    }
}
