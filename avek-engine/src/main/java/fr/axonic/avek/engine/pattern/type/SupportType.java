package fr.axonic.avek.engine.pattern.type;

import fr.axonic.avek.engine.support.Support;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by cduffau on 16/03/17.
 */
@XmlRootElement
@XmlSeeAlso({InputType.class,OutputType.class})
public abstract class SupportType<T extends Support> {
    protected Class type;

    SupportType(Class<T> type) {
        this.type = type;
    }

    @XmlElement
    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public boolean check(Support support){
        return type.isInstance(support);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupportType)) return false;

        SupportType<?> that = (SupportType<?>) o;

        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SupportType{" +
                "type=" + type +
                '}';
    }
}
