package fr.axonic.avek.meta.evidence;

public class Evidence<T extends Element> {
    private String name;
    private T element;

    public Evidence(String name, T element) {
        this.name = name;
        this.element = element;
    }
    public Evidence() {

    }

    public T getElement(){
        return element;
    }
}
