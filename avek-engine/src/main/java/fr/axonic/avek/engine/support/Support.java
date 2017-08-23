package fr.axonic.avek.engine.support;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.axonic.avek.engine.strategy.Actor;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Element;
import fr.axonic.avek.engine.support.evidence.Evidence;
import fr.axonic.avek.instance.conclusion.ExperimentationConclusion;
import fr.axonic.avek.instance.evidence.StimulationEvidence;
import fr.axonic.avek.instance.evidence.SubjectEvidence;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.UUID;

/**
 * Created by cduffau on 25/01/17.
 */
@XmlRootElement
@XmlSeeAlso({Conclusion.class, Evidence.class})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type")
public abstract class Support<T extends Element> implements Cloneable{
    protected String id, name;
    protected T element;

    public Support(String name, T element) {
        this.name = name;
        this.element = element;
        this.id= UUID.randomUUID().toString();
    }
    public Support() {
        this(null,null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evidence)) return false;

        Evidence<?> evidence = (Evidence<?>) o;

        if (name != null ? !name.equals(evidence.name) : evidence.name != null) return false;
        return element != null ? element.equals(evidence.element) : evidence.element == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (element != null ? element.hashCode() : 0);
        return result;
    }

    @XmlElement
    public T getElement(){
        return element;
    }


    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement
    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    @Override
    public Support clone() throws CloneNotSupportedException {
        Support res= (Support) super.clone();
        res.id = id;
        res.name = name;
        res.element=element;
        return res;
    }

    @Override
    public String toString() {
        return "Support{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", element=" + element +
                '}';
    }
}
