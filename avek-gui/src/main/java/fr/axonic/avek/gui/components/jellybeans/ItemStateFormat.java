package fr.axonic.avek.gui.components.jellybeans;

import java.util.function.Function;

/**
 * Created by nathael on 01/09/16.
 */
public class ItemStateFormat<T> {
    private final T linkedObject;
    private Function<T, String> getLabelMethod;
    private Function<T, String> getValueMethod;

    ItemStateFormat(T state) {
        this.linkedObject = state;

        // default are toString
        getLabelMethod = Object::toString;
        getValueMethod = getLabelMethod;
    }

    public void setGetLabelMethod(Function<T,String> getLabelMethod) {
        this.getLabelMethod = getLabelMethod;
    }
    public void setGetValueMethod(Function<T,String> getValueMethod) {
        this.getValueMethod = getValueMethod;
    }

    public String getLabel() {
        return getLabelMethod.apply(linkedObject);
    }
    public String getValue() {
        return getValueMethod.apply(linkedObject);
    }

    public T getObject() {
        return linkedObject;
    }


    @Override
    public String toString() {
        return "ItemStateFormat{'"+getLabel()+"'/'"+getValue()+"'}";
    }
}
