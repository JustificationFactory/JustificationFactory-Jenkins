package fr.axonic.avek.engine.support.evidence;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.axonic.avek.engine.kernel.Artifact;
import fr.axonic.base.engine.AStructure;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Objects;

/**
 * Created by cduffau on 22/06/16.
 */
@XmlRootElement
@JsonTypeInfo(
        use = JsonTypeInfo.Id.MINIMAL_CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type")

public abstract class Element extends AStructure implements Artifact {

    private String version;

    public Element(String version) {
        this.version = version;
    }

    public Element() {
    }

    @XmlElement
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        return equals(o,true);
    }

    public boolean equals(Object o, boolean checkVersion) {
        if (this == o) return true;
        if (!(o instanceof Element)) return false;
        if (!super.equals(o)) return false;
        Element element = (Element) o;
        return !checkVersion || Objects.equals(version, element.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), version);
    }
}
