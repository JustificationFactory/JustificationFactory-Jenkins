package fr.axonic.avek.engine.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.axonic.avek.engine.kernel.Artifact;
import fr.axonic.avek.engine.kernel.Assertion;
import fr.axonic.avek.engine.support.conclusion.Conclusion;
import fr.axonic.avek.engine.support.evidence.Element;
import fr.axonic.avek.engine.support.evidence.Evidence;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;
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
public abstract class Support<T extends Element> implements Assertion<T>, Cloneable{
    protected String id, name;
    protected T element;

    public Support(String name, T element) {
        this.name = name;
        this.element = element;
        this.id= UUID.randomUUID().toString().replace("-", "");
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

    public void setElement(T element) {
        this.element = element;
    }

    @Override
    public List<T> getArtifacts() {
        List<T> artifacts=new ArrayList<T>();
        artifacts.add(element);
        return artifacts;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    @XmlTransient
    @JsonIgnore
    public boolean isPrimitiveInputType(){
        return Evidence.class.isAssignableFrom(getClass());
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

    @Override
    public boolean isTerminal() {
        return false;
    }

    @Override
    public List<Assertion> conformsTo() {
        return null;
    }
}
