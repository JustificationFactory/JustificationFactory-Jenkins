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
    public String toString() {
        return "SupportType{" +
                "type=" + type +
                '}';
    }
}
