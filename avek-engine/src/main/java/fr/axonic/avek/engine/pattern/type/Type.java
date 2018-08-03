package fr.axonic.avek.engine.pattern.type;

import fr.axonic.avek.engine.kernel.Artifact;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class Type<T> implements Artifact {
    protected Class<T> classType;
    protected String nameType;

    public Type() {
    }

    public Type(Class<T> classType) {
        this.classType = classType;
    }

    public Type(Class<T> classType, String nameType) {
        this.classType = classType;
        this.nameType = nameType;
    }

    @XmlElement
    public Class<T> getClassType() {
        return classType;
    }

    public void setClassType(Class<T> classType) {
        this.classType = classType;
    }

    @XmlElement
    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Type)) return false;
        Type<?> type1 = (Type<?>) o;
        return Objects.equals(classType, type1.classType) &&
                Objects.equals(nameType, type1.nameType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classType, nameType);
    }

    @Override
    public String toString() {
        return "Type{" +
                "classType=" + classType +
                ", nameType='" + nameType + '\'' +
                '}';
    }
}
