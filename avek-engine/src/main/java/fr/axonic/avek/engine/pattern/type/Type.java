package fr.axonic.avek.engine.pattern.type;

import fr.axonic.avek.engine.kernel.Artifact;

import java.util.Objects;

public class Type<T> implements Artifact {
    protected Class<T> type;

    public Type() {
    }

    public Type(Class<T> type) {
        this.type=type;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Type)) return false;
        Type<?> type1 = (Type<?>) o;
        return Objects.equals(type, type1.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(type);
    }
}
