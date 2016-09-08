package fr.axonic.avek.engine.evidence;

public class Evidence<T extends Element> implements Cloneable{
    protected String name;
    protected T element;

    public Evidence(String name, T element) {
        this.name = name;
        this.element = element;
    }
    public Evidence() {

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

    public T getElement(){
        return element;
    }

    @Override
    public String toString() {
        return "Evidence{" +
                "name='" + name + '\'' +
                ", element=" + element +
                '}';
    }

    public String getName() {
        return name;
    }

    @Override
    public Evidence<T> clone() {
        return new Evidence<>(this.getName(),this.getElement());
    }
}
