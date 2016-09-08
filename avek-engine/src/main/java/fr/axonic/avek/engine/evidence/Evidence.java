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
    public Evidence<T> clone() throws CloneNotSupportedException {
        super.clone();
        return new Evidence<>(this.getName(),this.getElement());
    }
}
