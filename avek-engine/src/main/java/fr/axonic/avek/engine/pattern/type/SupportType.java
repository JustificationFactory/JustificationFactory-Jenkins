package fr.axonic.avek.engine.pattern.type;

import fr.axonic.avek.engine.kernel.Assertion;
import fr.axonic.avek.engine.support.Support;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cduffau on 16/03/17.
 */
@XmlRootElement
@XmlSeeAlso({InputType.class,OutputType.class})
public abstract class SupportType<T extends Support> implements Assertion<Type> {

    protected Type type;

    SupportType(Class<T> type) {
        this.type = new Type<>(type);
    }

    SupportType(Type type){
        this.type=type;
    }

    public SupportType() {
    }

    @XmlElement
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type=type;
    }

    public boolean check(Support support){
        if(type.getNameType()!=null && !type.getNameType().equals(support.getName())){
            return false;
        }
        return type.getClassType().isInstance(support);
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
                "type='" + type +
                '}';
    }

    @Override
    public List<Type> getArtifacts() {
        List<Type> artifacts=new ArrayList<>();
        artifacts.add(type);
        return artifacts;
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
